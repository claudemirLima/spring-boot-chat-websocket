/**
 * 
 */
    function getWebSocket() {
    	console.log("start");
        var webSocket = new WebSocket('ws://localhost:8080/chat/'+$('#username').text());
        webSocket.onopen = function (event) {
            console.log('WebSocket open connection');
        };

        webSocket.onmessage = function (event) {
            console.log('WebSocket Receives：%c' + event.data, 'color:green');
            //Receive Message from Server
            var message = JSON.parse(event.data) || {};
            var $messageContainer = $('.message-container');
            if (message.type === 'SPEAK') {
                $messageContainer.append(
                    '<div class="mdui-card" style="margin: 10px 0;">' +
                    '<div class="mdui-card-primary">' +
                    '<div class="mdui-card-content message-content">' + message.username + "：" + message.msg + '</div>' +
                    '</div></div>');
            }
            $('.chat-num').text(message.onlineCount);
            var $cards = $messageContainer.children('.mdui-card:visible').toArray();
            if ($cards.length > 5) {
                $cards.forEach(function (item, index) {
                    index < $cards.length - 5 && $(item).slideUp('fast');
                });
            }
        };

        /**
         * Close connection
         */
        webSocket.onclose = function (event) {
            console.log('WebSocket close connection.');
            
        };

        /**
         * Exception
         */
        webSocket.onerror = function (event) {
            console.log('WebSocket exception.');
        };
        return webSocket;
    }

    var webSocket = getWebSocket();


    /**
     * Send messages to server use webSocket.
     */
    function sendMsgToServer() {
    	webSocket.onopen();
        var $message = $('#msg');
        if ($message.val()) {
            webSocket.send(JSON.stringify({username: $('#username').text(), msg: $message.val()}));
            $message.val(null);
        }
    }

    /**
     * Clear screen
     */
    function clearMsg() {
        $(".message-container").empty();
    }

    /**
     * Enter to send message.
     */
    document.onkeydown = function (event) {
        var e = event || window.event || arguments.callee.caller.arguments[0];
        e.keyCode === 13 && sendMsgToServer();
    };
