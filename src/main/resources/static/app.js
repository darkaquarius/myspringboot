/**
 * Created by huishen on 17/12/16.
 */
var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
        $("#conversation2").show();
    }
    else {
        $("#conversation").hide();
        $("#conversation2").hide();
    }
    $("#greetings").html("");
    $("#auto-send-res").html("");
}

function connect() {
    // 1.端口
    var socket = new SockJS('/myspringboot-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        // 2.订阅
        stompClient.subscribe('/topic/greetings', function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        })

        // auto-send
        stompClient.subscribe('/topic/auto-send-res', function(autoSendRes) {
            showAutoSendRes(JSON.parse(autoSendRes.body).content);
        })
    });
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    // 3. 发送send
    stompClient.send("/app/hello", {}, JSON.stringify({'name' : $("#name").val()}));
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

function showAutoSendRes(autoSendRes) {
    $("#auto-send-res").append("<tr><td>" + autoSendRes + "</td></tr>");
}

function autoSend() {
    stompClient.send("/app/auto_send");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#connect").click(function () { connect(); });
    $("#disconnect").click(function () { disconnect(); });
    $("#send").click(function () { sendName(); });
    $("#auto-send").click(function () { autoSend(); });
})