// ==================== 工具函数 ====================
const $ = (id) => document.getElementById(id);

function setTip(msg, ok){
    const tip = $("tip");
    tip.textContent = msg || "";
    tip.className = "mini-tip " + (ok ? "ok" : "bad");
}

function escapeHtml(s){
    return String(s ?? "")
        .replaceAll("&","&amp;")
        .replaceAll("<","&lt;")
        .replaceAll(">","&gt;")
        .replaceAll('"',"&quot;")
        .replaceAll("'","&#39;");
}

function toInt(x){
    if(x === null || x === undefined) return null;
    if(typeof x === "string" && x.trim() === "") return null;
    const n = Number(x);
    if(!Number.isFinite(n)) return null;
    const i = Math.trunc(n);
    return Number.isFinite(i) ? i : null;
}

// ==================== API 地址 ====================
const API = {
    register: "/players/register",
    login: "/players/login",
    showSkill: "/players/skills/showskill",
    showSlot: "/players/skills/showslot",
    upgrade: "/players/skills/upgrade",
    updatePlayer: "/players/update",
    showEnemy: "/enemys/showenemy",
    initFight: "/initfight",
    equipSkill: "/players/skills/equip",
    useSkill: "/useskill",
    reward: "/reward",
    showMedicine: "/players/showmedicine",
    useMedicine: "/usemedicine"
};

// ==================== 网络请求 ====================
async function postJson(url,data){
    console.log(`POST ${url}`, data);
    const res = await fetch(url,{
        method:"POST",
        headers:{ "Content-Type":"application/json" },
        body:JSON.stringify(data)
    });
    return await res.json();
}
async function getJson(url){
    const res = await fetch(url, { method:"GET" });
    return await res.json();
}

// ==================== 玩家缓存 ====================
function getPlayerCache(){
    return JSON.parse(localStorage.getItem("player") || "null");
}
function setPlayerCache(p){
    localStorage.setItem("player", JSON.stringify(p));
}

// ==================== 渲染玩家信息面板 ====================
function renderPlayer(p){
    const box = $("playerPanel");
    const skillWrap = $("skillBtnWrap");
    const bagWrap = $("bagBtnWrap");
    if(!p){
        box.style.display="none";
        skillWrap.style.display="none";
        if(bagWrap) bagWrap.style.display="none";
        return;
    }
    box.style.display="block";
    skillWrap.style.display="block";
    if(bagWrap) bagWrap.style.display="block";
    box.innerHTML =
        `<b>${escapeHtml(p.userName)}</b><br>` +
        `Lv: ${escapeHtml(p.lv)}<br>` +
        `HP: ${escapeHtml(p.hp)}<br>` +
        `MP: ${escapeHtml(p.mp)}<br>` +
        `Money: ${escapeHtml(p.money)}<br>` +
        `Exp: ${escapeHtml(p.exp)} / ${escapeHtml(p.expMax)}` +
        `<button class="update-btn" id="btnUpdatePlayer">更新状态</button>`;

    $("btnUpdatePlayer").onclick = async ()=>{
        const cur = getPlayerCache();
        if(!cur || cur.id == null){ setTip("请先登录", false); return; }
        try{
            const r = await postJson(API.updatePlayer, { playerId: cur.id });
            if(r && r.code === 1){
                if(r.data){
                    renderPlayer(r.data);
                    setPlayerCache(r.data);
                }
                setTip("状态已更新", true);
            }else{
                setTip((r && r.msg) ? r.msg : "更新失败", false);
            }
        }catch(e){
            setTip("更新失败", false);
        }
    };
}

// ==================== 登录/注册 ====================
$("registerBtn").onclick = async ()=>{
    const userName=$("userName").value.trim();
    const password=$("password").value;
    if(!userName||!password){ setTip("不能为空",false); return; }
    const r=await postJson(API.register,{userName,password});
    if(r.code===1) setTip("注册成功",true);
    else setTip(r.msg,false);
};

$("loginBtn").onclick = async ()=>{
    const userName=$("userName").value.trim();
    const password=$("password").value;
    if(!userName||!password){ setTip("不能为空",false); return; }

    const r=await postJson(API.login,{userName,password});
    if(r.code===1){
        setTip("登录成功",true);
        renderPlayer(r.data);
        setPlayerCache(r.data);
    }else{
        setTip(r.msg,false);
        renderPlayer(null);
        localStorage.removeItem("player");
    }
};

// ==================== 技能模块 ====================
let currentMode = "normal";
let lastSkillsCache = null;
let lastSlotsCache = null;
let skillIdNameMap = null;

function openModal(){ $("skillModal").style.display="flex"; }
function closeModal(){
    $("skillModal").style.display="none";
    $("modalBody").innerHTML = "";
    lastSkillsCache = null;
    lastSlotsCache = null;
    skillIdNameMap = null;
}
$("closeModal").onclick = closeModal;
$("skillModal").addEventListener("click", (e)=>{
    if(e.target === $("skillModal")) closeModal();
});

function filterSkillsByMode(list, mode){
    if(!Array.isArray(list)) return [];
    if(mode === "god"){
        return list.filter(s => toInt(s.type) === 2);
    }
    return list.filter(s => [1,3,4].includes(toInt(s.type)));
}

function buildSkillIdNameMap(allSkills){
    const map = new Map();
    (allSkills || []).forEach(s=>{
        const id = toInt(s.id);
        if(id != null){
            map.set(id, (s.skillName ?? ""));
        }
    });
    return map;
}

function renderSlots(slots, idNameMap){
    const arr = new Array(9).fill(null);
    (slots || []).forEach(it=>{
        const idx = toInt(it.slotIndex);
        if(idx != null && idx >= 1 && idx <= 8){
            arr[idx] = it;
        }
    });

    let boxes = "";
    for(let i=1;i<=8;i++){
        const it = arr[i];
        const skillId = it ? toInt(it.skillId) : null;

        if(skillId == null){
            boxes += `<div class="slot empty">空</div>`;
            continue;
        }

        const name = idNameMap && idNameMap.get(skillId) ? idNameMap.get(skillId) : "";
        const text = name ? name : "未知技能";
        boxes += `<div class="slot">${escapeHtml(text)}</div>`;
    }

    return `<div class="slot-wrap">${boxes}</div>`;
}

function buildSlotOptions(){
    let opts = `<option value="">槽位</option>`;
    for(let i=1;i<=8;i++){
        opts += `<option value="${i}">${i}</option>`;
    }
    return opts;
}

function renderSkillTableNormal(list){
    const rows = list.map(s => {
        const skillId = toInt(s.id);
        const name = s.skillName ?? "";
        const limitedLv = s.limitedLv ?? "";
        const lvRaw = s.lv ?? "";
        const lvInt = toInt(lvRaw) ?? 0;
        const curATK = s.curATK ?? "";
        const curMpCost = s.curMpCost ?? "";
        const curUpgradeCost = s.curUpgradeCost ?? "";

        const equipDisabled = (skillId==null || lvInt === 0) ? "disabled" : "disabled";

        return `
        <tr>
          <td>${escapeHtml(name)}</td>
          <td>${escapeHtml(limitedLv)}</td>
          <td>${escapeHtml(lvRaw)}</td>
          <td>${escapeHtml(curATK)}</td>
          <td>${escapeHtml(curMpCost)}</td>
          <td>${escapeHtml(curUpgradeCost)}</td>
          <td>
            <button class="up-btn" data-skill-id="${skillId ?? ""}" ${skillId==null ? "disabled" : ""}>升级</button>
          </td>
          <td>
            <select class="equip-select" data-skill-id="${skillId ?? ""}" data-skill-lv="${lvInt}">
              ${buildSlotOptions()}
            </select>
            <button class="equip-btn"
                    data-skill-id="${skillId ?? ""}"
                    data-skill-lv="${lvInt}"
                    ${equipDisabled}>装备</button>
          </td>
        </tr>
      `;
    }).join("");

    return `
      <table class="skill-table">
        <thead>
          <tr>
            <th style="width:17%">name</th>
            <th style="width:10%">limitedlv</th>
            <th style="width:6%">lv</th>
            <th style="width:10%">curatk</th>
            <th style="width:15%">curmpcost</th>
            <th style="width:15%">curupgradecost</th>
            <th style="width:10%">upgrade</th>
            <th style="width:17%">equip</th>
          </tr>
        </thead>
        <tbody>
          ${rows || `<tr><td colspan="8" style="opacity:.8">暂无数据</td></tr>`}
        </tbody>
      </table>
    `;
}

function renderSkillTableGod(list){
    const rows = list.map(s => {
        const skillId = toInt(s.id);
        const name = s.skillName ?? "";
        const lvRaw = s.lv ?? "";
        const lvInt = toInt(lvRaw) ?? 0;
        const curATK = s.curATK ?? "";
        const curMpCost = s.curMpCost ?? "";
        const curUpgradeCost = s.curUpgradeCost ?? "";

        const equipDisabled = (skillId==null || lvInt === 0) ? "disabled" : "disabled";

        return `
        <tr>
          <td>${escapeHtml(name)}</td>
          <td>${escapeHtml(lvRaw)}</td>
          <td>${escapeHtml(curATK)}</td>
          <td>${escapeHtml(curMpCost)}</td>
          <td>${escapeHtml(curUpgradeCost)}</td>
          <td>
            <button class="up-btn" data-skill-id="${skillId ?? ""}" ${skillId==null ? "disabled" : ""}>升级</button>
          </td>
          <td>
            <select class="equip-select" data-skill-id="${skillId ?? ""}" data-skill-lv="${lvInt}">
              ${buildSlotOptions()}
            </select>
            <button class="equip-btn"
                    data-skill-id="${skillId ?? ""}"
                    data-skill-lv="${lvInt}"
                    ${equipDisabled}>装备</button>
          </td>
        </tr>
      `;
    }).join("");

    return `
      <div class="small">
        <table class="skill-table">
          <thead>
            <tr>
              <th style="width:22%">name</th>
              <th style="width:7%">lv</th>
              <th style="width:12%">curatk</th>
              <th style="width:17%">curmpcost</th>
              <th style="width:17%">curupgradecost</th>
              <th style="width:10%">upgrade</th>
              <th style="width:15%">equip</th>
            </tr>
          </thead>
          <tbody>
            ${rows || `<tr><td colspan="7" style="opacity:.8">暂无数据</td></tr>`}
          </tbody>
        </table>
      </div>
    `;
}

function setTabActive(){
    const tn = $("tabNormal");
    const tg = $("tabGod");
    if(currentMode === "god"){
        tg.classList.add("active");
        tn.classList.remove("active");
        $("modalTitle").textContent = "神技";
    }else{
        tn.classList.add("active");
        tg.classList.remove("active");
        $("modalTitle").textContent = "技能";
    }
}

function renderAll(){
    const allSkills = lastSkillsCache || [];
    const slots = (lastSlotsCache || []).slice().sort((a,b)=> (toInt(a.slotIndex) ?? 0) - (toInt(b.slotIndex) ?? 0));
    const idNameMap = skillIdNameMap || new Map();

    const slotHtml = renderSlots(slots, idNameMap);

    let tableHtml = "";
    if(currentMode === "god"){
        tableHtml = renderSkillTableGod(filterSkillsByMode(allSkills, "god"));
    }else{
        tableHtml = renderSkillTableNormal(filterSkillsByMode(allSkills, "normal"));
    }

    $("modalBody").innerHTML = slotHtml + tableHtml;
    setTabActive();
}

$("tabNormal").onclick = ()=>{
    currentMode = "normal";
    renderAll();
};
$("tabGod").onclick = ()=>{
    currentMode = "god";
    renderAll();
};

$("modalBody").addEventListener("change", (e)=>{
    const sel = e.target.closest(".equip-select");
    if(!sel) return;

    const skillId = toInt(sel.getAttribute("data-skill-id"));
    const lv = toInt(sel.getAttribute("data-skill-lv")) ?? 0;
    const slotIndex = toInt(sel.value);

    const tr = sel.closest("tr");
    const equipBtn = tr ? tr.querySelector(".equip-btn") : null;
    if(!equipBtn) return;

    equipBtn.disabled = !(skillId != null && lv > 0 && slotIndex != null);
});

$("modalBody").addEventListener("click", async (e)=>{
    const btn = e.target.closest(".up-btn");
    if(btn){
        const p = getPlayerCache();
        if(!p || p.id == null){ setTip("请先登录", false); return; }

        const skillId = toInt(btn.getAttribute("data-skill-id"));
        if(skillId == null){ setTip("skillId 无效", false); return; }

        btn.disabled = true;
        btn.textContent = "升级中";

        try{
            await doUpgrade(p.id, skillId);

            const skills = await fetchSkills(p.id);
            lastSkillsCache = skills;
            skillIdNameMap = buildSkillIdNameMap(skills);

            renderAll();

            const up = await doUpdatePlayer(p.id);
            if(up && up.data){
                renderPlayer(up.data);
                setPlayerCache(up.data);
            }

            setTip("升级成功", true);
        }catch(err){
            setTip(String(err.message || err), false);
            renderAll();
        }
        return;
    }

    const equipBtn = e.target.closest(".equip-btn");
    if(equipBtn){
        const p = getPlayerCache();
        if(!p || p.id == null){ setTip("请先登录", false); return; }

        const tr = equipBtn.closest("tr");
        const sel = tr ? tr.querySelector(".equip-select") : null;

        const skillId = toInt(equipBtn.getAttribute("data-skill-id"));
        const lv = toInt(equipBtn.getAttribute("data-skill-lv")) ?? 0;
        const slotIndex = sel ? toInt(sel.value) : null;

        if(skillId == null){ setTip("skillId 无效", false); return; }
        if(lv === 0){ setTip("等级为0不可装备", false); return; }
        if(slotIndex == null){ setTip("请选择槽位(1-8)", false); return; }

        equipBtn.disabled = true;
        equipBtn.textContent = "装备中";

        try{
            await doEquipSkill(p.id, skillId, slotIndex);

            const slots = await fetchSlots(p.id);
            lastSlotsCache = slots;

            renderAll();
            setTip("装备成功", true);
        }catch(err){
            setTip(String(err.message || err), false);
            renderAll();
        }
        return;
    }
});

$("openSkillBtn").onclick = async ()=>{
    const p = getPlayerCache();
    if(!p || p.id == null){
        setTip("请先登录", false);
        return;
    }

    setTip("", true);
    $("modalBody").innerHTML = `<div class="hint">加载中...</div>`;
    openModal();

    try{
        const playerId = p.id;

        const [skills, slots] = await Promise.all([
            fetchSkills(playerId),
            fetchSlots(playerId)
        ]);

        lastSkillsCache = skills;
        lastSlotsCache = slots;
        skillIdNameMap = buildSkillIdNameMap(skills);

        renderAll();
    }catch(err){
        $("modalBody").innerHTML =
            `<div class="hint">加载失败：<code>${escapeHtml(String(err.message || err))}</code></div>`;
    }
};

async function fetchSkills(playerId){
    const r = await postJson(API.showSkill, { playerId });
    if(r && r.code === 1) return Array.isArray(r.data) ? r.data : [];
    throw new Error((r && r.msg) ? r.msg : "查询技能失败");
}
async function fetchSlots(playerId){
    const r = await postJson(API.showSlot, { playerId });
    if(r && r.code === 1) return Array.isArray(r.data) ? r.data : [];
    throw new Error((r && r.msg) ? r.msg : "查询槽位失败");
}
async function doUpgrade(playerId, skillId){
    const r = await postJson(API.upgrade, { playerId, skillId });
    if(r && r.code === 1) return r;
    throw new Error((r && r.msg) ? r.msg : "升级失败");
}
async function doUpdatePlayer(playerId){
    const r = await postJson(API.updatePlayer, { playerId });
    if(r && r.code === 1) return r;
    throw new Error((r && r.msg) ? r.msg : "更新失败");
}
async function doEquipSkill(playerId, skillId, slotIndex){
    const r = await postJson(API.equipSkill, { playerId, skillId, slotIndex });
    if(r && r.code === 1) return r;
    throw new Error((r && r.msg) ? r.msg : "装备失败");
}

// ==================== 背包模块 ====================
const bagModal = $("bagModal");
const bagModalBody = $("bagModalBody");
const openBagBtn = $("openBagBtn");
const closeBagModal = $("closeBagModal");
const bagTabMedicine = $("bagTabMedicine");
const bagTabEquip = $("bagTabEquip");
const bagTabItem = $("bagTabItem");

function openBagModal(){
    bagModal.style.display = "flex";
    setBagTabActive('item');
    bagModalBody.innerHTML = `<div class="hint" style="text-align:center; padding:40px 0;">👜 物品功能暂未开放</div>`;
}
function closeBagModalFn(){
    bagModal.style.display = "none";
}
function setBagTabActive(tab){
    [bagTabEquip, bagTabMedicine, bagTabItem].forEach(btn => btn.classList.remove('active'));
    if(tab === 'medicine') bagTabMedicine.classList.add('active');
    else if(tab === 'equip') bagTabEquip.classList.add('active');
    else if(tab === 'item') bagTabItem.classList.add('active');
}

openBagBtn.onclick = ()=>{
    const p = getPlayerCache();
    if(!p || p.id == null){
        setTip("请先登录", false);
        return;
    }
    openBagModal();
};

closeBagModal.onclick = closeBagModalFn;
bagModal.addEventListener("click", (e)=>{
    if(e.target === bagModal) closeBagModalFn();
});

bagTabMedicine.onclick = async ()=>{
    const p = getPlayerCache();
    if(!p || p.id == null){
        setTip("请先登录", false);
        closeBagModalFn();
        return;
    }
    setBagTabActive('medicine');
    bagModalBody.innerHTML = `<div class="hint">加载药品中...</div>`;
    try{
        const medicineList = await doShowMedicine(p.id);
        bagModalBody.innerHTML = renderMedicineTable(medicineList);
    }catch(err){
        bagModalBody.innerHTML = `<div class="hint">加载失败：<code>${escapeHtml(err.message)}</code></div>`;
    }
};

bagTabEquip.onclick = ()=>{
    setBagTabActive('equip');
    bagModalBody.innerHTML = `<div class="hint" style="text-align:center;">⚔️ 装备功能暂未开放</div>`;
};

bagTabItem.onclick = ()=>{
    setBagTabActive('item');
    bagModalBody.innerHTML = `<div class="hint" style="text-align:center;">👜 物品功能暂未开放</div>`;
};

async function doShowMedicine(playerId){
    const r = await postJson(API.showMedicine, { playerId });
    if(r && r.code === 1) return Array.isArray(r.data) ? r.data : [];
    throw new Error((r && r.msg) ? r.msg : "查询药品失败");
}

function renderMedicineTable(medicines){
    if(!medicines.length){
        return `<div class="hint" style="text-align:center;">暂无药品</div>`;
    }
    const rows = medicines.map(m => {
        const name = m.name ?? '未知';
        const hp = m.restoreHp ?? 0;
        const mp = m.restoreMp ?? 0;
        const num = m.number ?? 0;
        return `<tr>
            <td>${escapeHtml(name)}</td>
            <td>${escapeHtml(hp)}</td>
            <td>${escapeHtml(mp)}</td>
            <td>${escapeHtml(num)}</td>
        </tr>`;
    }).join('');
    return `<table class="medicine-table">
        <thead><tr><th>名称</th><th>恢复血量</th><th>恢复蓝量</th><th>拥有个数</th></tr></thead>
        <tbody>${rows}</tbody>
    </table>`;
}

// ==================== 战斗列表模块 ====================
function openEnemyPanel(){
    $("enemyPanel").style.display = "block";
}
function closeEnemyPanel(){
    $("enemyPanel").style.display = "none";
    $("enemyPanelBody").innerHTML = "";
}
$("enemyPanelClose").onclick = closeEnemyPanel;

$("enemyFloatBtn").onclick = async ()=>{
    openEnemyPanel();
    $("enemyPanelBody").innerHTML = `<div class="hint">加载中...</div>`;

    try{
        const r = await getJson(API.showEnemy);
        if(r && r.code === 1){
            const list = Array.isArray(r.data) ? r.data : [];
            if(list.length === 0){
                $("enemyPanelBody").innerHTML = `<div class="hint">暂无怪物</div>`;
                return;
            }

            const html = list.map(e=>{
                const name = e.name ?? "";
                const lv = e.enemyLv ?? "";
                const id = e.id ?? "";
                const basicHp = e.basicHp ?? "";
                return `
                    <div class="enemy-row"
                         data-enemy-id="${escapeHtml(id)}"
                         data-enemy-name="${escapeHtml(name)}"
                         data-enemy-lv="${escapeHtml(lv)}"
                         data-enemy-hp="${escapeHtml(basicHp)}">
                        <div class="label">${escapeHtml(name)} lv${escapeHtml(lv)}</div>
                        <button class="fight-btn">战斗</button>
                    </div>
                `;
            }).join("");

            $("enemyPanelBody").innerHTML = html;
        }else{
            $("enemyPanelBody").innerHTML =
                `<div class="hint">加载失败：<code>${escapeHtml((r && r.msg) ? r.msg : "unknown")}</code></div>`;
        }
    }catch(err){
        $("enemyPanelBody").innerHTML =
            `<div class="hint">加载失败：<code>${escapeHtml(String(err.message || err))}</code></div>`;
    }
};

// ==================== 战斗模块 ====================
let battleFightCache = null;

function openBattleScreen(){ $("battleScreen").style.display = "block"; }
function closeBattleScreen(){
    $("battleScreen").style.display = "none";
    $("battleTitle").textContent = "战斗";
    $("battlePlayerBox").innerHTML = "";
    $("battleEnemyBox").innerHTML = "";
    $("battleLog").innerHTML = "";
    $("battleSkillRow1").innerHTML = "";
    $("battleSkillRow2").innerHTML = "";
    battleFightCache = null;
    hideMedicineOverlay();
}
$("battleBack").onclick = closeBattleScreen;

function renderBattleTopByFight(fight){
    const pName = fight?.playerName ?? "";
    const pLv   = fight?.playerLv ?? "";
    const pHpMax = fight?.playerHpMax ?? "";
    const pMpMax = fight?.playerMpMax ?? "";
    const pHpCur = fight?.curPlayerHp ?? "";
    const pMpCur = fight?.curPlayerMp ?? "";

    const eName = fight?.enemyName ?? "";
    const eLv   = fight?.enemyLv ?? "";
    const eHpMax = fight?.enemyHpMax ?? "";
    const eHpCur = fight?.curEnemyHp ?? "";

    $("battlePlayerBox").innerHTML =
        `<div class="line">${escapeHtml(pName)} lv${escapeHtml(pLv)}</div>` +
        `<div class="line">hp：${escapeHtml(pHpCur)}/${escapeHtml(pHpMax)}</div>` +
        `<div class="line">mp：${escapeHtml(pMpCur)}/${escapeHtml(pMpMax)}</div>`;

    $("battleEnemyBox").innerHTML =
        `<div class="line">${escapeHtml(eName)} lv${escapeHtml(eLv)}</div>` +
        `<div class="line">hp：${escapeHtml(eHpCur)}/${escapeHtml(eHpMax)}</div>`;
}

function renderBattleLogByFight(fight){
    const logs = Array.isArray(fight?.log) ? fight.log : [];
    if(logs.length === 0){
        $("battleLog").innerHTML =
            `<div class="log-box"><div class="log-empty">暂无log（你还没写入内容）</div></div>`;
        return;
    }
    const html = logs.map(line =>
        `<div class="log-line">${escapeHtml(String(line ?? ""))}</div>`
    ).join("");
    $("battleLog").innerHTML = `<div class="log-box">${html}</div>`;
}

function renderBattleSkillSlots(slots, idNameMap){
    const arr = new Array(9).fill(null);
    (slots || []).forEach(it=>{
        const idx = toInt(it.slotIndex);
        if(idx != null && idx >= 1 && idx <= 8){
            arr[idx] = it;
        }
    });

    const row1 = [], row2 = [];
    for(let i=1;i<=8;i++){
        const it = arr[i];
        const skillId = it ? toInt(it.skillId) : null;

        let text = "空";
        let cls = "skill-slot-btn empty";
        let disabled = "disabled";
        let dataSkillId = "";

        if(skillId != null){
            const name = idNameMap && idNameMap.get(skillId) ? idNameMap.get(skillId) : "";
            text = name ? name : "未知技能";
            cls = "skill-slot-btn";
            disabled = "";
            dataSkillId = String(skillId);
        }

        const btnHtml = `<button class="${cls}"
                                 type="button"
                                 data-skill-id="${escapeHtml(dataSkillId)}"
                                 ${disabled}>${escapeHtml(text)}</button>`;
        if(i<=4) row1.push(btnHtml);
        else row2.push(btnHtml);
    }

    $("battleSkillRow1").innerHTML = row1.join("");
    $("battleSkillRow2").innerHTML = row2.join("");
}

async function updateBattleControls(fight){
    const backBtn = $("battleBack");
    const pHp = Number(fight?.curPlayerHp ?? 0);
    const eHp = Number(fight?.curEnemyHp ?? 0);

    const ended = (pHp <= 0) || (eHp <= 0);
    backBtn.disabled = !ended;

    const skillBtns = $("battleScreen").querySelectorAll(".skill-slot-btn");
    skillBtns.forEach(b=>{
        const sid = toInt(b.getAttribute("data-skill-id"));
        const isEmpty = !sid;
        if(ended){
            b.disabled = true;
        }else{
            b.disabled = isEmpty;
        }
    });

    if(ended){
        const p = getPlayerCache();
        if(pHp <= 0){
            alert("你失败了");
        }else if(eHp <= 0){
            alert("你胜利了");
            try{
                if(p && p.id){
                    const rewardResult = await doReward(p.id);
                    if(rewardResult) showRewardModal(rewardResult);
                    const updateResult = await doUpdatePlayer(p.id);
                    if(updateResult && updateResult.data){
                        renderPlayer(updateResult.data);
                        setPlayerCache(updateResult.data);
                    }
                }
            }catch(err){
                console.error("获取奖励失败：", err);
            }
        }
    }
}

$("battleScreen").addEventListener("click", async (e)=>{
    const btn = e.target.closest(".skill-slot-btn");
    if(!btn || btn.disabled) return;

    const p = getPlayerCache();
    if(!p || p.id == null){ setTip("请先登录", false); return; }

    const skillId = toInt(btn.getAttribute("data-skill-id"));
    if(skillId == null) return;

    if(!battleFightCache){ setTip("战斗未初始化", false); return; }

    const skillBtns = $("battleScreen").querySelectorAll(".skill-slot-btn");
    skillBtns.forEach(b=> b.disabled = true);

    try{
        const fight = await doUseSkill(p.id, skillId);
        battleFightCache = fight;
        $("battleTitle").textContent = `战斗：${fight?.enemyName ?? ""} lv${fight?.enemyLv ?? ""}`;
        renderBattleTopByFight(fight);
        renderBattleLogByFight(fight);
        updateBattleControls(fight);
    }catch(err){
        setTip(String(err.message || err), false);
        if(battleFightCache) updateBattleControls(battleFightCache);
        else $("battleBack").disabled = true;
    }
});

$("enemyPanelBody").addEventListener("click", async (e)=>{
    const btn = e.target.closest(".fight-btn");
    if(!btn) return;

    const row = e.target.closest(".enemy-row");
    if(!row) return;

    const p = getPlayerCache();
    if(!p || p.id == null){ setTip("请先登录", false); return; }

    const enemy = {
        id: toInt(row.getAttribute("data-enemy-id")),
        name: row.getAttribute("data-enemy-name"),
        enemyLv: toInt(row.getAttribute("data-enemy-lv")),
        basicHp: toInt(row.getAttribute("data-enemy-hp"))
    };

    if(enemy.id == null || enemy.enemyLv == null){
        setTip("enemyId/enemyLv 无效", false);
        return;
    }

    openBattleScreen();
    $("battleBack").disabled = true;
    $("battleTitle").textContent = `战斗：加载中...`;
    $("battlePlayerBox").innerHTML = `<div class="hint">加载中...</div>`;
    $("battleEnemyBox").innerHTML = `<div class="hint">加载中...</div>`;
    $("battleLog").innerHTML = `<div class="log-box"><div class="log-empty">加载中...</div></div>`;
    $("battleSkillRow1").innerHTML = `<div class="hint">加载中...</div>`;
    $("battleSkillRow2").innerHTML = ``;

    try{
        const fight = await doInitFight(p.id, enemy.id, enemy.enemyLv);
        battleFightCache = fight;
        $("battleTitle").textContent = `战斗：${fight?.enemyName ?? ""} lv${fight?.enemyLv ?? ""}`;
        renderBattleTopByFight(fight);
        renderBattleLogByFight(fight);

        const [skills, slots] = await Promise.all([
            fetchSkills(p.id),
            fetchSlots(p.id)
        ]);
        lastSkillsCache = skills;
        lastSlotsCache = slots;
        skillIdNameMap = buildSkillIdNameMap(skills);

        renderBattleSkillSlots(slots, skillIdNameMap);
        updateBattleControls(fight);
    }catch(err){
        setTip(String(err.message || err), false);
        $("battlePlayerBox").innerHTML = `<div class="hint">初始化失败：<code>${escapeHtml(String(err.message || err))}</code></div>`;
        $("battleEnemyBox").innerHTML = `<div class="hint">请返回重试</div>`;
        $("battleLog").innerHTML = `<div class="log-box"><div class="log-empty">log不可用</div></div>`;
        $("battleSkillRow1").innerHTML = ``;
        $("battleSkillRow2").innerHTML = ``;
        $("battleBack").disabled = false;
    }
});

async function doInitFight(playerId, enemyId, enemyLv){
    const r = await postJson(API.initFight, { playerId, enemyId, enemyLv });
    if(r && r.code === 1) return r.data;
    throw new Error((r && r.msg) ? r.msg : "初始化战斗失败");
}
async function doUseSkill(playerId, skillId){
    const r = await postJson(API.useSkill, { playerId, skillId });
    if(r && r.code === 1) return r.data;
    throw new Error((r && r.msg) ? r.msg : "使用技能失败");
}
async function doReward(playerId){
    const r = await postJson(API.reward, { playerId });
    if(r && r.code === 1) return r.data;
    throw new Error((r && r.msg) ? r.msg : "获取奖励失败");
}

// ==================== 药品覆盖层（网格分页版） ====================
const medicineOverlay = $("medicineOverlay");
const medicineGrid = $("medicineGrid");
const closeMedicineOverlay = $("closeMedicineOverlay");
const prevMedicinePage = $("prevMedicinePage");
const nextMedicinePage = $("nextMedicinePage");
const medicinePageInfo = $("medicinePageInfo");

let medicineListData = [];          // 当前加载的所有药品
let currentMedicinePage = 1;
let totalMedicinePages = 1;

function showMedicineOverlay() {
    medicineOverlay.style.display = "flex";
}
function hideMedicineOverlay() {
    medicineOverlay.style.display = "none";
}

closeMedicineOverlay.onclick = () => {
    hideMedicineOverlay();
};

// 更新分页按钮状态
function updatePaginationButtons() {
    prevMedicinePage.disabled = currentMedicinePage <= 1;
    nextMedicinePage.disabled = currentMedicinePage >= totalMedicinePages;
    medicinePageInfo.textContent = `${currentMedicinePage}/${totalMedicinePages}`;
}

// 渲染当前页药品网格
function renderMedicineGrid() {
    if (!medicineListData.length) {
        medicineGrid.innerHTML = `<div class="hint" style="grid-column:span 4; text-align:center;">暂无药品</div>`;
        totalMedicinePages = 1;
        currentMedicinePage = 1;
        updatePaginationButtons();
        return;
    }

    totalMedicinePages = Math.ceil(medicineListData.length / 8);
    if (currentMedicinePage > totalMedicinePages) currentMedicinePage = totalMedicinePages;
    if (currentMedicinePage < 1) currentMedicinePage = 1;

    const start = (currentMedicinePage - 1) * 8;
    const pageItems = medicineListData.slice(start, start + 8);

    let html = '';
    for (let i = 0; i < 8; i++) {
        const medicine = pageItems[i];
        if (medicine) {
            const id = medicine.id;
            const name = medicine.name || '未知';
            const hp = medicine.restoreHp || 0;
            const mp = medicine.restoreMp || 0;
            const num = medicine.number || 0;
            // 组装显示文本：名称 恢复血量：xx 恢复蓝量：xx 个数：x
            const infoText = `${name} 恢复血量：${hp} 恢复蓝量：${mp} 个数：${num}`;
            html += `
                <div class="medicine-slot">
                    <span class="medicine-info" title="${escapeHtml(infoText)}">${escapeHtml(infoText)}</span>
                    <button class="use-medicine-btn" data-medicine-id="${escapeHtml(id)}" ${num <= 0 ? 'disabled' : ''}>使用</button>
                </div>
            `;
        } else {
            // 空槽位
            html += `<div class="medicine-slot empty">空</div>`;
        }
    }
    medicineGrid.innerHTML = html;
    updatePaginationButtons();

    // 绑定使用按钮事件
    medicineGrid.querySelectorAll('.use-medicine-btn').forEach(btn => {
        btn.addEventListener('click', async (e) => {
            const medicineId = toInt(btn.getAttribute('data-medicine-id'));
            if (!medicineId) return;
            btn.disabled = true;
            const p = getPlayerCache();
            if (!p || p.id == null) {
                setTip("请先登录", false);
                btn.disabled = false;
                return;
            }
            try {
                const fight = await doUseMedicine(p.id, medicineId);
                battleFightCache = fight;
                $("battleTitle").textContent = `战斗：${fight?.enemyName ?? ""} lv${fight?.enemyLv ?? ""}`;
                renderBattleTopByFight(fight);
                renderBattleLogByFight(fight);
                updateBattleControls(fight);
                hideMedicineOverlay();

                // 使用后重新加载药品列表，以便更新数量（可选）
                const medicines = await doShowMedicine(p.id);
                medicineListData = medicines;
                renderMedicineGrid();
            } catch (err) {
                setTip(String(err.message || err), false);
                btn.disabled = false;
            }
        });
    });
}

// 上一页
prevMedicinePage.onclick = () => {
    if (currentMedicinePage > 1) {
        currentMedicinePage--;
        renderMedicineGrid();
    }
};

// 下一页
nextMedicinePage.onclick = () => {
    if (currentMedicinePage < totalMedicinePages) {
        currentMedicinePage++;
        renderMedicineGrid();
    }
};

$("bagBtn").onclick = async () => {
    if (!battleFightCache) {
        setTip("没有进行中的战斗", false);
        return;
    }
    const p = getPlayerCache();
    if (!p || p.id == null) {
        setTip("请先登录", false);
        return;
    }
    showMedicineOverlay();
    medicineGrid.innerHTML = `<div class="hint" style="grid-column:span 4; text-align:center;">加载药品中...</div>`;
    try {
        const medicines = await doShowMedicine(p.id);
        medicineListData = medicines;
        currentMedicinePage = 1;
        renderMedicineGrid();
    } catch (err) {
        medicineGrid.innerHTML = `<div class="hint" style="grid-column:span 4; text-align:center;">加载失败：${escapeHtml(err.message)}</div>`;
    }
};

async function doUseMedicine(playerId, medicineId) {
    const r = await postJson(API.useMedicine, { playerId, medicineId });
    if (r && r.code === 1) return r.data;
    throw new Error((r && r.msg) ? r.msg : "使用药品失败");
}

// ==================== 奖励弹窗 ====================
function showRewardModal(rewardData){
    if(!rewardData) return;
    let rewardHtml = "";
    if(rewardData.exp != null){
        rewardHtml += `<div class="reward-item"><div class="reward-label">经验</div><div class="reward-value">+${escapeHtml(rewardData.exp)}</div></div>`;
    }
    if(rewardData.money != null){
        rewardHtml += `<div class="reward-item"><div class="reward-label">金币</div><div class="reward-value">+${escapeHtml(rewardData.money)}</div></div>`;
    }
    if(rewardHtml){
        $("rewardModalBody").innerHTML = rewardHtml + `
            <div class="reward-total"><div class="label">总计奖励</div><div class="value">已获得</div></div>
            <button class="reward-continue-btn" id="continueAfterReward">继续</button>
        `;
        $("rewardModal").style.display = "flex";
        $("continueAfterReward").onclick = () => { $("rewardModal").style.display = "none"; };
    }
}
$("closeRewardBtn").onclick = () => { $("rewardModal").style.display = "none"; };

// ==================== 初始化 ====================
const cache = getPlayerCache();
if(cache) renderPlayer(cache);