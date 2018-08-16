var userName = document.getElementById("userName"),
    challengedPlayers = document.getElementsByName("player"),
    challengeBtn = document.getElementById("challengeBtn");

// function sendChallenge(opp) {
//     location.href = "/challenge?userName=" + userName.value() + "&oppName=" + opp.value();
// }

challengeBtn.onclick = function() {
    var url = "";

    for (var i = 0; i < challengedPlayers.length; i++) {
        if (challengedPlayers.item(i).checked === true) {
            url += challengedPlayers.item(i).value + "#";
        }
    }
    if (url !== "") {
        location.href = "/challenge?challengedPlayers=" + url;
    } else {
        alert("Please select at least one player to challenge!");
    }
};


// window.setInterval(function(){
//     function check() {
//         if (getParam("userName") !== -1) {
//             location.href = "/challenge?refresh=1";
//         }
//     }
// }, 2000);

function getParam(parameter) {
    var urlParams = new URLSearchParams(window.location.search);
    if (urlParams.has(parameter)) {
        return urlParams.get(parameter);
    } else {
        return -1;
    }
}

