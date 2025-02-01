const apiUrl = "http://localhost:8080/api/match";
const socketUrl = "http://localhost:8080/scoreboard-websocket";
let stompClient = null;

function connectWebSocket() {
    const socket = new SockJS(socketUrl);
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function () {
        stompClient.subscribe('/topic/scoreboard', function (messageOutput) {
            const matchData = JSON.parse(messageOutput.body);
            document.getElementById("team-a-name").value = matchData.teamAName;
            document.getElementById("team-b-name").value = matchData.teamBName;
            document.getElementById("team-a-score").textContent = matchData.teamAScore;
            document.getElementById("team-b-score").textContent = matchData.teamBScore;
        });
    });
}

function updateMatch() {
    const data = {
        teamAName: document.getElementById("team-a-name").value,
        teamBName: document.getElementById("team-b-name").value
    };
    fetch(apiUrl, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data),
    });
}

function incrementScore(team) {
    fetch(apiUrl)
        .then(response => response.json())
        .then(matchData => {
            if (team === "A") matchData.teamAScore++;
            if (team === "B") matchData.teamBScore++;
            fetch(apiUrl, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(matchData),
            });
        });
}

window.onload = function () {
    connectWebSocket();
}
