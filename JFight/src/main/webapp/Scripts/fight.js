var attBoxes = document.getElementsByName("attackBox"),
    defBoxes = document.getElementsByName("defBox"),
    userName = document.getElementById("userName").innerText,
    userId = document.getElementById("userId").value,
    round = parseInt(document.getElementById("roundNr").innerText),
    endBtn = document.getElementById("endTurn"),
    fightStatus = document.getElementById("fightStatus").value;

window.onload = function () {
    if (fightStatus !== "FIGHTING") {
        var fightOutcomeText = document.getElementById("fightOutcomeText");

        if (fightStatus === "WINNER") {
            fightOutcomeText.innerText = "You are  VICTORIOUS!";
        } else if (fightStatus === "LOSER") {
            fightOutcomeText.innerText = "You have  LOST  the fight...";
        } else {
            fightOutcomeText.innerText = "You both fall to each others blades... it's a  DRAW!";
        }

        $('#fightEndStatus').modal('show');
        console.log(fightStatus);
        endBtn.innerText = "Exit Fight";
        endBtn.onclick = function () {
            location.href = "/news";
        }
    }
};

function checkMarked(cb) {
    var cBoxes = document.getElementsByName(cb.name);
    var limit = 2;
    var count = 0;
    for (var i = 0; i < cBoxes.length; i++) {
        if (cBoxes.item(i).checked === true) {
            count++;
        }
    }
    if (count > limit) {
        cb.checked = false;
        alert("Please check only two boxes for Attack/Defence");
    }
}

endBtn.onclick = function() {
    var url = "";
    var attCounter = 1;
    var defCounter = 1;
    for (var i = 0; i < attBoxes.length; i++) {
        if (attBoxes.item(i).checked === true) {
            url += "&att" + attCounter + "=" + attBoxes.item(i).id.replace("att", "");
            attCounter++;
        }
    }
    for (var i = 0; i < defBoxes.length; i++) {
        if (defBoxes.item(i).checked === true) {
            url += "&def" + defCounter + "=" + defBoxes.item(i).id.replace("def", "");
            defCounter++;
        }
    }

    var fightId = getParam("fightId");
    round += 1;
    url += "&fightId=" + fightId + "&round=" + round;
    endBtn.innerText = "Waiting for other player";
    location.href = "/fight?" + url;
};

var countDownDate = 31000 + new Date().getTime();

// Update the count down every 1 second
var x = setInterval(function() {
    // Find the distance between now an the count down date
    var distance = countDownDate - new Date().getTime();

    var seconds = Math.floor((distance % (1000 * 60)) / 1000);

    // Display the result in the element with id="demo"
    document.getElementById("roundTime").innerText = seconds + "s";

    // If the count down is finished, write some text
    if (distance < 0) {
        clearInterval(x);
        document.getElementById("roundTime").innerText = "Time has ran out.";
    }
}, 1000);

 function getParam(parameter) {
    var urlParams = new URLSearchParams(window.location.search);
    if (urlParams.has(parameter)) {
        return urlParams.get(parameter);
    } else {
        return -1;
    }
}
