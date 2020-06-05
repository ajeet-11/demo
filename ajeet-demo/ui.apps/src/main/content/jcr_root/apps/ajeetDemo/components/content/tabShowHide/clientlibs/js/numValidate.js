(function (document, $, ns) {
    "use strict";
    $(document).on("click", ".cq-dialog-submit", function (e) {
        e.stopPropagation();
        e.preventDefault();

        var $form = $(this).closest("form.foundation-form"),
            symbolid = $form.find("[name='./symbol']").val(),
               message, clazz = "coral-Button ",
         patterns = {
             symboladd: /^([0-9]{10})\.?$/i
        };

        if(symbolid != "" && !patterns.symboladd.test(symbolid) && (symbolid != null)) {
                ns.ui.helpers.prompt({
                title: Granite.I18n.get("Invalid Number"),
                message: "Please Enter a valid 10 Digit Phone Number",
                actions: [{
                    id: "CANCEL",
                    text: "CANCEL",
                    className: "coral-Button"
                }],
            callback: function (actionId) {
                if (actionId === "CANCEL") {
                }
            }
        });
        }else{
                 $form.submit();
        }
    });
})(document, Granite.$, Granite.author);