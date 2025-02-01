const socket = new SockJS("http://localhost:8080/scoreboard-websocket");
const stompClient = Stomp.over(socket);

stompClient.connect({}, () => {
    stompClient.subscribe("/topic/scoreboard", (message) => {
        const data = JSON.parse(message.body);
        document.getElementById("team-a-name").innerText = data.teamAName;
        document.getElementById("team-a-score").innerText = data.teamAScore;
        document.getElementById("team-b-name").innerText = data.teamBName;
        document.getElementById("team-b-score").innerText = data.teamBScore;
    });
});
