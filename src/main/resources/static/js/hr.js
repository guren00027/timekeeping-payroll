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

function addRow(username, timeIn, timeOut){
  const tr = document.createElement("tr");
  tr.innerHTML = `
    <td style="padding:10px; border-bottom:1px solid var(--border);">${username}</td>
    <td style="padding:10px; border-bottom:1px solid var(--border);">${fmt(timeIn)}</td>
    <td style="padding:10px; border-bottom:1px solid var(--border);">${fmt(timeOut)}</td>
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

  data.forEach(a => addRow(a.user?.username || "(unknown)", a.timeIn, a.timeOut));
});