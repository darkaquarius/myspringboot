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
        //$("#conversation3").show();
    }
    else {
        $("#conversation").hide();
        $("#conversation2").hide();
        //$("#conversation3").hide();
    }
    $("#greetings").html("");
    $("#auto-send-res").html("");
    //$("#bitcoins-tickers-res").html("");
}

function connect() {
    // 1.端口
    var url = 'http://localhost:8082/myspringboot-websocket';
    //var url = 'http://localhost:8080/cqbit-api';
    var socket = new SockJS(url);
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        // 2.订阅
        stompClient.subscribe('/topic/greetings', function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });

         //auto-send
        stompClient.subscribe('/topic/auto-send-res', function(autoSendRes) {
            showAutoSendRes(JSON.parse(autoSendRes.body).content);
        });

        // bitcoins-tickers-send
        //stompClient.subscribe('/topic/tickers', function(tickersRes) {
        //    showBitcoinsTickersRes(JSON.parse(tickersRes.body).content);
        //});

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

function autoSend() {
    stompClient.send("/app/auto_send");
}

//function bitcoinsTickersSend() {
//    stompClient.send("/app/tickers");
//}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

function showAutoSendRes(autoSendRes) {
    $("#auto-send-res").append("<tr><td>" + autoSendRes + "</td></tr>");
}

//function showBitcoinsTickersRes(bitcoinsTickersRes) {
//    $("#bitcoins-tickers-res").append("<tr><td>" + bitcoinsTickersRes + "</td></tr>");
//}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#connect").click(function () { connect(); });
    $("#disconnect").click(function () { disconnect(); });
    $("#send").click(function () { sendName(); });
    $("#auto-send").click(function () { autoSend(); });
    //$("#bitcoins-tickers-send").click(function () { bitcoinsTickersSend(); });
})