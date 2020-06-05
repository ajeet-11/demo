(function(document, $) {
    "use strict";
    $(document).on("foundation-contentloaded", function(e) {
        showHide($(".cq-dialog-tab-showhide", e.target));
    });

    $(document).on("selected", ".cq-dialog-tab-showhide", function(e) {
        showHide($(this));
    });

    $(document).on("change", ".cq-dialog-tab-showhide", function(e) {
        showHide($(this));
    });

   function showHide(el){
       el.each(function(i, element) {
         var target = $(element).data("cqDialogTabShowhideTarget");
           if ($(element).data("select")) {
               var value = $(element).data("select").getValue();

               $(target).not(".hide").addClass("hide");
               if(value)
      $(target+'.'+value).removeClass("hide");
           }

       })
   }

})(document,Granite.$);
