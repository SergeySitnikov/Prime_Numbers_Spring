jQuery(function($) {
    $(document).ready(function () {
            var token = $("meta[name=_csrf]").attr("content");
            var header = $("meta[name=_csrf_header]").attr("content");
            $(document).ajaxSend(function (e, xhr, options) {
                xhr.setRequestHeader(header, token);
            });

            $("form[name=userForm]").submit(function (e) {
                e.preventDefault();
                $.ajax({
                    url: "/registration",
                    data: $(this).serialize(),
                    type: "POST",
                    async: false,
                    success: function (res) {
                        if (res.validated) {
                            $(location).attr('href', '/registration/confirm');
                        } else {
                            $.each(res.errorMessages, function (key, value) {
                            });
                        }
                    },
                    error: function (res) {
                        $(location).attr('href', '/error');
                    }
                });

            });
        }
    );
});





