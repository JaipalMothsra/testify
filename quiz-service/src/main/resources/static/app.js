function goToDashboard() {
    window.location.href = "dashboard.html";
}


async function startQuiz(topic, difficulty) {

    const response = await fetch(
        "http://localhost:8080/api/quiz/start",
        {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                topic: topic,
                difficulty: difficulty
            })
        }
    );

    const attemptId = await response.text();

    localStorage.setItem("attemptId", attemptId);

    window.location.href = "quiz.html";
}