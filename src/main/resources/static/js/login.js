/**
 * 
 */

/**
     * Login
     */
    function login() {
		console.log('test');
       document.getElementById("login").submit();
    }

    /**
     * Enter to login.
     */
    document.onkeydown = function (event) {
        var e = event || window.event || arguments.callee.caller.arguments[0];
        e.keyCode === 13 && login();
    };