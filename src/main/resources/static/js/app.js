const statusText = document.getElementById("statusText");
const timeInText = document.getElementById("timeInText");
const timeOutText = document.getElementById("timeOutText");
const msg = document.getElementById("message");
const btnIn = document.getElementById("btnTimeIn");
const btnOut = document.getElementById("btnTimeOut");

function fmt(dt){
  if(!dt) return "-";
  return new Date(dt).toLocaleString();
}

function setMessage(text){
  msg.textContent = text || "";
}

async function loadToday(){
  setMessage("");
  const res = await fetch("/employee/attendance/today");
  if(!res.ok){
    statusText.textContent = "Unable to load status";
    btnIn.disabled = true;
    btnOut.disabled = true;
    return;
  }

  const data = await res.json(); // may be null if no record
  if(!data){
    statusText.textContent = "Not timed in yet";
    timeInText.textContent = "-";
    timeOutText.textContent = "-";
    btnIn.disabled = false;
    btnOut.disabled = true;
    return;
  }

  timeInText.textContent = fmt(data.timeIn);
  timeOutText.textContent = fmt(data.timeOut);

  if(!data.timeIn){
    statusText.textContent = "Not timed in yet";
    btnIn.disabled = false;
    btnOut.disabled = true;
  } else if(data.timeIn && !data.timeOut){
    statusText.textContent = "Timed in (Time out available)";
    btnIn.disabled = true;
    btnOut.disabled = false;
  } else {
    statusText.textContent = "Completed for today";
    btnIn.disabled = true;
    btnOut.disabled = true;
  }
}

async function post(url){
  setMessage("");
  const res = await fetch(url, { method: "POST" });
  const text = await res.text();

  if(res.ok){
    setMessage("Success");
    await loadToday();
    return;
  }

  // try to parse our JSON error
  try {
    const err = JSON.parse(text);
    setMessage(err.message || "Request failed");
  } catch {
    setMessage("Request failed");
  }
}

btnIn.addEventListener("click", () => post("/employee/attendance/time-in"));
btnOut.addEventListener("click", () => post("/employee/attendance/time-out"));

loadToday();