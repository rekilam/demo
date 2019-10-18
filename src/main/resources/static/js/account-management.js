

$(document).ready(function () {
    //console.log("document");
    validateForm();
    setEmpyField();
    autopage();
});

var setEmpyField = function() {
    $("#btnAddAccount").click(function () {
        $('#addModalTitle').text("Add Account");
        $('#email').val("");  $('#email').attr('readonly', false);
        $('#passWord').val("");
        $('#fullName').val("");
        $('#address').val("");
        $('#phone').val("");
        $("input[name='sex']")[0].checked = true;
        $("input[name='isAdmin']")[0].checked = true;
        $("#birth").val("");
        $("form .error").hide(); //hide validation
        $("form input").show(); //when hide validation, input the same class 'error' was hided, so show again
        $("#addAccountModal .input_time").css("color: black !important;");
    });  
}
var validateForm = function () {
    console.log("validate");
    $("#addAccountForm").validate({
        rules: {
            email: {
                required: true,
                email: true
            },
            passWord: {
                required: true,
                minlength: 6
            },
            fullName: "required",
            birth: "required",
            address: "required",
            phone: {
                required: true,
                minlength: 10,
                maxlength: 13
            }
        },
        messages: {
            passWord: {
                required: "Please provide a password",
                minlength: "Your password must be at least 6 characters long"
            },
            fullName: "Please enter your fullname",
            email: "Please enter a valid email address",
            birth: "Please choose your birthday",
            address: "Please enter your address",
            phone: "Please enter a valid phonenumber"
        },
        submitHandler: function (form) { // Make sure the form is submitted to the destination defined
            form.submit();
        }
    });
};

var getJsonAccount = function (accountId) {
    $.ajax({
        type: "POST",
        url: "/find-account?id=" + accountId,
        cache: false,
        timeout: 600000,
        success: function (data) {
            $('#accountId').val(data.accountId);
            $('#addModalTitle').text("Update Account [ID: " + data.accountId + "]");
            $('#email').val(data.email);
            $('#passWord').val(data.passWord);
            $('#fullName').val(data.fullName);
            $('#address').val(data.address);
            $('#phone').val(data.phone);

            if (data.sex == "Nam") {
                $("input[name='sex']")[0].checked = true;
            } else {
                $("input[name='sex']")[1].checked = true;
            }
            
            if (data.isAdmin == false) {
                $("input[name='isAdmin']")[0].checked = true;
            } else {
                $("input[name='isAdmin']")[1].checked = true;
            }
            var birth = new Date(data.birth);
            $("#birth").val(birth.getDate() + "/" + (birth.getMonth() + 1) + "/" + birth.getFullYear());
            if (birth.getDate() < 10 && (birth.getMonth() + 1) < 10) {
                $("#birth").val(birth.getFullYear() + "-0" + (birth.getMonth() + 1) + "-0" + birth.getDate());
            } else if (birth.getDate() < 10 && (birth.getMonth() + 1) >= 10) {
                $("#birth").val(birth.getFullYear() + "-" + (birth.getMonth() + 1) + "-0" + birth.getDate());
            } else if ((birth.getMonth() + 1) < 10 && birth.getDate() >= 10) {
                $("#birth").val(birth.getFullYear() + "-0" + (birth.getMonth() + 1) + "-" + birth.getDate());
            } else {
                $("#birth").val(birth.getFullYear() + "-" + (birth.getMonth() + 1) + "-" + birth.getDate());
            }
        },
        error: function (e) {
            console.log("error");
        }
    });
}

var getJsonAccount2 = function (accountId) {
    $.get("find-account", {
        id: accountId
    }, function (data) {
        if (data.check == "fail") {
            alert("Loaddata failed!");
            return;
        }
        $('#accountId').val(data.accountId);
        $('#addModalTitle').text("Update Account [ID: " + data.accountId + "]");
        $('#email').val(data.email);
        $('#email').prop('readonly', true);
        $('#passWord').val(data.passWord);
        $('#fullName').val(data.fullName);
        $('#address').val(data.address);
        $('#phone').val(data.phone);

        if (data.sex == "Nam") {
            $("input[name='sex']")[0].checked = true;
        } else {
            $("input[name='sex']")[1].checked = true;
        }

        var birth = new Date(data.birth);
        $("#birth").val(birth.getDate() + "/" + (birth.getMonth() + 1) + "/" + birth.getFullYear());
        if (birth.getDate() < 10 && (birth.getMonth() + 1) < 10) {
            $("#birth").val(birth.getFullYear() + "-0" + (birth.getMonth() + 1) + "-0" + birth.getDate());
        } else if (birth.getDate() < 10 && (birth.getMonth() + 1) >= 10) {
            $("#birth").val(birth.getFullYear() + "-" + (birth.getMonth() + 1) + "-0" + birth.getDate());
        } else if ((birth.getMonth() + 1) < 10 && birth.getDate() >= 10) {
            $("#birth").val(birth.getFullYear() + "-0" + (birth.getMonth() + 1) + "-" + birth.getDate());
        } else {
            $("#birth").val(birth.getFullYear() + "-" + (birth.getMonth() + 1) + "-" + birth.getDate());
        }
    });
};

function autopage() {
    $('.page-number').remove(); //k xóa là nó sinh ra phân trang hoài
    $('table.paginated').each(function() {
        var currentPage = 0;
        var numPerPage = 5; /* Muốn hiển thị 1 page có bao nhiêu hàng */
        var $table = $(this);
        $table.bind('repaginate', function() {
            $table.find('tbody tr').hide().slice(currentPage * numPerPage, (currentPage + 1) * numPerPage).show();
        });
        $table.trigger('repaginate');
        var numRows = $table.find('tbody tr').length;
        var numPages = Math.ceil(numRows / numPerPage);
        var $pager = $('<div class="pager"></div>');
        var pre = "",
            next = "next";
        if (numPages > 1) {
            pre = "«";
            next = "»";
        } else {
            pre = "";
            next = "";
        }
        if (numPages > 1) {
            $('<span class="page-number"></span>').text(pre).bind('click', {
                newPage: page
            }, function(event) {
                currentPage = currentPage - 1;
                if (currentPage < 0) {
                    currentPage = 0;
                } else if (currentPage > numPages) {
                    currentPage = numPages - 1;
                }
                $table.trigger('repaginate');
                $(this).addClass('active').siblings().removeClass('active');

            }).appendTo($pager).addClass('clickable');
        }
        for (var page = 0; page < numPages; page++) {
            $('<span class="page-number"></span>').text(page + 1).bind('click', {
                newPage: page
            }, function(event) {
                currentPage = event.data['newPage'];
                $table.trigger('repaginate');
                $(this).addClass('active').siblings().removeClass('active');
            }).appendTo($pager).addClass('clickable');
        }
        if (numPages > 1) {
            $('<span class="page-number"></span>').text(next).bind('click', {
                newPage: page
            }, function(event) {
                currentPage = currentPage + 1;
                if (currentPage < 0) {
                    currentPage = 0;
                } else if (currentPage > numPages) {
                    currentPage = numPages - 1;
                }

                if (currentPage < numPages) {
                    $table.trigger('repaginate');
                    $(this).addClass('active').siblings().removeClass('active');

                }
            }).appendTo($pager).addClass('clickable');

            $pager.insertAfter($table).find('span.page-number:first').addClass('active').css({
                'margin-left': '500px'
            });
        }
    });
}

var mySlidenav_flag = 0;

function openNav() {
    if (mySlidenav_flag == 0) {
        document.getElementById("mySidenav").style.width = "250px";
        document.getElementById("account-myMain").style.marginLeft = "250px";
        document.getElementById("showhideMenu").style.display = "none";
        mySlidenav_flag = 1;
    } else {
        document.getElementById("mySidenav").style.width = "0";
        document.getElementById("account-myMain").style.marginLeft = "0";
        document.getElementById("showhideMenu").style.display = "block";
        mySlidenav_flag = 0;
    }

}

function closeNav() {
    if (mySlidenav_flag == 0) {
        mySlidenav_flag = 1;
    } else {
        mySlidenav_flag = 0;
    }
    document.getElementById("mySidenav").style.width = "0";
    document.getElementById("account-myMain").style.marginLeft = "0";
    document.getElementById("showhideMenu").style.display = "block";
}