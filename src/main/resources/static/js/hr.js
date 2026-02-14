const dateInput = document.getElementById("dateInput");
const btnLoad = document.getElementById("btnLoad");
const msg = document.getElementById("hrMessage");
const tbody = document.querySelector("#tbl tbody");

function setMessage(t){ msg.textContent = t || ""; }

function fmt(dt){
  if(!dt) return "-";
  return new Date(dt).toLocaleString();
}

function clearTable(){
  tbody.innerHTML = "";
}

function addRow(username, timeIn, timeOut, workedHours){
  const tr = document.createElement("tr");
  tr.innerHTML = `
    <td style="padding:10px; border-bottom:1px solid var(--border);">${username}</td>
    <td style="padding:10px; border-bottom:1px solid var(--border);">${fmt(timeIn)}</td>
    <td style="padding:10px; border-bottom:1px solid var(--border);">${fmt(timeOut)}</td>
    <td style="padding:10px; border-bottom:1px solid var(--border);">${workedHours?.toFixed(2) || "0.00"}</td>
  `;
  tbody.appendChild(tr);
}

btnLoad.addEventListener("click", async () => {
  setMessage("");
  clearTable();

  const date = dateInput.value;
  if(!date){
    setMessage("Please select a date.");
    return;
  }

  const res = await fetch(`/hr/attendance/by-date?date=${date}`);
  if(!res.ok){
    setMessage("Failed to load attendance.");
    return;
  }

  const data = await res.json();
  if(data.length === 0){
    setMessage("No records found.");
    return;
  }

//  data.forEach(a => addRow(a.user?.username || "(unknown)", a.timeIn, a.timeOut));
  data.forEach(a => addRow(a.username, a.timeIn, a.timeOut, a.workedHours));
});

const payrollDateInput = document.getElementById("payrollDateInput");
const btnLoadCutoff = document.getElementById("btnLoadCutoff");
const cutoffMsg = document.getElementById("cutoffMessage");
const cutoffTbody = document.querySelector("#cutoffTbl tbody");

function setCutoffMessage(t){ cutoffMsg.textContent = t || ""; }
function clearCutoffTable(){ cutoffTbody.innerHTML = ""; }

function addCutoffRow(username, totalHours, daysIn, daysOut){
  const tr = document.createElement("tr");
  tr.innerHTML = `
    <td style="padding:10px; border-bottom:1px solid var(--border);">${username}</td>
    <td style="padding:10px; border-bottom:1px solid var(--border);">${(totalHours ?? 0).toFixed(2)}</td>
    <td style="padding:10px; border-bottom:1px solid var(--border);">${daysIn ?? 0}</td>
    <td style="padding:10px; border-bottom:1px solid var(--border);">${daysOut ?? 0}</td>
  `;
  cutoffTbody.appendChild(tr);
}

btnLoadCutoff.addEventListener("click", async () => {
  setCutoffMessage("");
  clearCutoffTable();

  const payrollDate = payrollDateInput.value;
  if(!payrollDate){
    setCutoffMessage("Please select a payroll date.");
    return;
  }

  const res = await fetch(`/hr/payroll/cutoff-summary?payrollDate=${payrollDate}`);
  const text = await res.text();

  if(!res.ok){
    try {
      const err = JSON.parse(text);
      setCutoffMessage(err.message || "Failed to load cutoff summary.");
    } catch {
      setCutoffMessage("Failed to load cutoff summary.");
    }
    return;
  }

  const data = JSON.parse(text);
  if(data.length === 0){
    setCutoffMessage("No records found.");
    return;
  }

  data.forEach(r => addCutoffRow(r.username, r.totalWorkedHours, r.daysWithTimeIn, r.daysWithTimeOut));
});