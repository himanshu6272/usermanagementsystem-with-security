$(document).ready(function () {




        if($(".table-bordered").find('tr').length == 1){
                $(".table-container").addClass('d-none');
            }else{
                 $(".table-container").removeClass('d-none');
            }

    var manageAddresses = function(){
        let addresses = $("#addresses").find("tr");
                    for(let i = 0; i<addresses.length;i++){
                    let address = addresses[i];
                    let aid = address.getElementsByClassName("aid")[0];
                    let street = address.getElementsByClassName("street")[0];
                    let city = address.getElementsByClassName("city")[0];
                    let state = address.getElementsByClassName("state")[0];
                    let zip = address.getElementsByClassName("zip")[0];
                    let country = address.getElementsByClassName("country")[0];
                    aid.setAttribute("name", "addresses["+i+"].aid");
                    street.setAttribute("name", "addresses["+i+"].street");
                    city.setAttribute("name", "addresses["+i+"].city");
                    state.setAttribute("name", "addresses["+i+"].state");
                    zip.setAttribute("name", "addresses["+i+"].zip");
                    country.setAttribute("name", "addresses["+i+"].country");
                    }
    }
    manageAddresses();

  var flag = true;
  let message = $("#message").text();
  if (message === null || message === '' || message === undefined) {
    flag = false;
  }
  if (flag) {
    console.log("error has content");
    setTimeout(function () {
      $(".alert-div").addClass("d-none");
    }, 3000);
  }


  $("#inputStreet").keyup(function () {
    if ($(this).val() === "" || $(this).val() === undefined) {
      $("#streetHelp").html("street is required").addClass("text-danger");
    } else {
      $("#streetHelp").empty();
    }
  })

  $("#inputCity").keyup(function () {
    if ($(this).val() === "" || $(this).val() === undefined) {
      $("#cityHelp").html("city is required").addClass("text-danger");
    } else {
      $("#cityHelp").empty();
    }
  })

  $("#inputState").keyup(function () {
    if ($(this).val() === "" || $(this).val() === undefined) {
      $("#stateHelp").html("state is required").addClass("text-danger");
    } else {
      $("#stateHelp").empty();
    }
  })

  $("#inputZip").keyup(function () {
    let zipExp = /^[0-9]{6,7}$/;
    if ($(this).val() === "" || $(this).val() === undefined) {
      $("#zipHelp").html("zip is required").addClass("text-danger");
    } else if (zipExp.test($(this).val()) == false) {
      $("#zipHelp")
        .html("enter valid zip code!")
        .addClass("text-danger");
    } else {
      $("#zipHelp").empty();
    }
  })

  $("#inputCountry").keyup(function () {
    if ($(this).val() === "" || $(this).val() === undefined) {
      $("#countryHelp").html("country is required").addClass("text-danger");
    } else {
      $("#countryHelp").empty();
    }
  })


  var id = 0;
  $("#save-address-btn").click(function () {
    //                $(".table-container").removeClass("d-none").addClass("border").addClass("border-warning");
    let street = $("#inputStreet").val();
    let city = $("#inputCity").val();
    let state = $("#inputState").val();
    let zip = $("#inputZip").val();
    let country = $("#inputCountry").val();

    if (street === "" || street === undefined) {
      $("#streetHelp").html("street is required").addClass("text-danger");
    } else {
      $("#streetHelp").empty();
    }
    if (city === "" || city === undefined) {
      $("#cityHelp").html("city is required").addClass("text-danger");
    } else {
      $("#cityHelp").empty();
    }
    if (state === "" || state === undefined) {
      $("#stateHelp").html("state is required").addClass("text-danger");
    } else {
      $("#stateHelp").empty();
    }

    let zipExp = /^[0-9]{6,7}$/;
    if (zip === "" || zip === undefined) {
      $("#zipHelp").html("zip is required").addClass("text-danger");
    } else if (zipExp.test(zip) == false) {
      $("#zipHelp")
        .html("enter valid zip code!")
        .addClass("text-danger");
    } else if (country === "" || country === undefined) {
      $("#countryHelp").html("country is required").addClass("text-danger");
    } else {
      $("#zipHelp").empty();
      $("#countryHelp").empty();
      let addElement = '<tr><td><input hidden="hidden" name="addressid"><input type="number" class="aid" name="aid" hidden="hidden" value="0"><input readonly class="street" name="addresses['+id+'].street" value="' + street +
        '"></td><td><input readonly class="city" name="addresses['+id+'].city" value="' + city +
        '"></td><td><input readonly class="state" name="addresses['+id+'].state" value="' + state +
        '"></td><td><input readonly class="zip" name="addresses['+id+'].zip" value="' + zip +
        '"></td><td><input readonly class="country" name="addresses['+id+'].country" value="' + country +
        '"></td><td><input class="btn btn-warning py-0 px-1 edit-address-btn" type="button" value="Update"></td></tr>';
      //                let addElement = '<div class="address border border-black p-2 m-3"><input hidden name="addressId"<label>Street</label><br><input readonly class="street form-control" name="addresses['+id+'].street" value="'+street+
      //                            '"><label>City</label><br><input readonly class="city form-control" name="addresses['+id+'].city" value="'+city+
      //                            '"><label>State</label><br><input readonly class="state form-control" name="addresses['+id+'].state" value="'+state+
      //                            '"><label>Zip</label><br><input readonly class="zip form-control" name="addresses['+id+'].zip" value="'+zip+
      //                            '"><label>Country</label><br><input readonly class="country form-control" name="addresses['+id+'].country" value="'+country+
      //                            '"></div><small class="form-text addressesHelp"></small></div>';
      $("#addresses").append(addElement);
      manageAddresses();
      $(".table-container").removeClass("d-none");
      $("#staticBackdrop").modal('hide');
      $("#inputStreet").val("");
      $("#inputCity").val("");
      $("#inputState").val("");
      $("#inputZip").val("");
      $("#inputCountry").val("");
      id = id + 1;
    }

  })




  $(".remove-user-btn").click(function () {
    $(this).closest("tr").remove();
  });

  $(document).on("click", ".remove-address-btn", function () {
    $(this).closest("tr").remove();
    if($(".table-bordered").find('tr').length == 1){
                    $(".table-container").addClass('d-none');
                }else{
                     $(".table-container").removeClass('d-none');
                     manageAddresses();

    }

  });

  $(document).on("click", ".edit-address-btn", function () {
    $(this).removeAttr("value").removeClass("edit-address-btn").attr("value", "Done").addClass("update-address-btn");
    $(this).closest("tr").find("input").removeAttr("readonly");
  });
  $(document).on("click", ".update-address-btn", function () {
    $(this).removeAttr("value").removeClass("update-address-btn").attr("value", "Update").addClass("edit-address-btn");
    $(this).closest("tr").find("input").attr("readonly", "readonly");
  });

  let firstName = function () {
    let fname = $("#fname").val();
    let nameExp = /^[a-zA-Z]{2,8}$/;
    if (fname == "" || fname == undefined) {
      $("#fnameHelp").html("first name is required!").addClass("text-danger");
      return false;
    } else if (nameExp.test(fname) == false) {
      $("#fnameHelp")
        .html(
          "name should have atleat two character and does not contain any digit"
        )
        .addClass("text-danger");
      return false;
    } else {
      $("#fnameHelp").empty();
      return true;
    }
  };

  let lastName = function () {
    let lname = $("#lname").val();
    let nameExp = /^[a-zA-Z]{2,8}$/;
    if (lname == "" || lname == undefined) {
      $("#lnameHelp").html("last name is required!").addClass("text-danger");
      return false;
    } else if (nameExp.test(lname) == false) {
      $("#lnameHelp")
        .html(
          "name should have atleat two character and does not contain any digit"
        )
        .addClass("text-danger");
      return false;
    } else {
      $("#lnameHelp").empty();
      return true;
    }
  };

  let mobileNumber = function () {
    let mobNum = $("#mobile-number").val();
    let mobExp = /^[0-9]{10,11}$/;
    if (mobNum == "" || mobNum == undefined) {
      $("#mobileHelp")
        .html("mobile number is required!")
        .addClass("text-danger");
      return false;
    } else if (mobExp.test(mobNum) == false) {
      $("#mobileHelp")
        .html("enter valid mobile number!")
        .addClass("text-danger");
      return false;
    } else {
      $("#mobileHelp").empty();
      return true;
    }
  };

  let emailAddress = function () {
    let emailExp = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
    let email = $("#email-address").val();
    if (email == "" || email == undefined) {
      $("#emailHelp").html("email is required!").addClass("text-danger");
      return false;
    } else if (emailExp.test(email) == false) {
      $("#emailHelp")
        .html("Invalid email, email must contain @ (example@xyz.com)!")
        .addClass("text-danger");
      return false;
    } else {
      $("#emailHelp").empty();
      return true;
    }
  };

  let dateOfBirth = function () {
    let dob = $("#dob").val();
    let currentDate = new Date().toISOString().split("T")[0];
    if (dob == "" || dob == undefined) {
      $("#dateHelp").html("enter date of birth!");
      return false;
    } else if (dob > currentDate) {
      $("#dateHelp").html("please enter valid date!");
      return false;
    } else {
      $("#dateHelp").empty();
      return true;
    }
  };

  let securityQue = function () {
    let answer = $("#security-answer").val();
    if (answer == "" || answer == undefined) {
      $("#securityanswerHelp")
        .html("please enter the answer!")
        .addClass("text-danger");
      return false;
    } else {
      $("#securityanswerHelp").empty();
      return true;
    }
  };

  let pwd = function () {
    let password = $("#password").val();
    let passwordExp = /^(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,16}$/;
    if (password == "" || password == undefined) {
      $("#passwordHelp").html("password is required!").addClass("text-danger");
      return false;
    } else if (passwordExp.test(password) == false) {
      $("#passwordHelp")
        .html(
          "password should contain atleast 8 characters(one uppercase, one lowercase, one digit and one special character"
        )
        .addClass("text-danger");
      return false;
    } else {
      $("#passwordHelp").empty();
      return true;
    }
  };

  let cnfPassword = function () {
    let password = $("#password").val();
    let cnfPwd = $("#cnf-password").val();
    if (cnfPwd == "" || cnfPwd == undefined) {
      $("#cnfpasswordHelp")
        .html("please re-enter password!")
        .addClass("text-danger");
      return false;
    } else if (cnfPwd != password) {
      $("#cnfpasswordHelp")
        .html("password does not matched!")
        .addClass("text-danger");
      return false;
    } else {
      $("#cnfpasswordHelp").empty();
      return true;
    }
  };

  let profilePhoto = function () {
    let photo = $("#profile-photo").val();
    if (photo === "" || photo === undefined) {
      $("#profilephotoHelp")
        .html("please upload the profile photo!")
        .addClass("text-danger");
      return false;
    }
    else {
      $("#profilephotoHelp").empty();
      return true;
    }

  }
  let gender = function () {
    let selectedGender = $(".gender:checked").val();

    if (selectedGender === undefined) {
      $("#genderHelp").html("please select the gender");
      return false;
    } else {
      $("#genderHelp").empty();
      return true;
    }
  };

  let role = function () {
    let selectedRole = $(".role:checked").val();
    if (selectedRole === undefined) {
      $("#roleHelp").html("please select the role");
      return false;
    } else {
      $("#roleHelp").empty();
      return true;
    }
  };



    $("#dob").on('change', function(){
        dateOfBirth();
      });

    $("#calendar-icon").click(function () {
        $("#dob").datepicker("show");
      });
      $("#dob, #calendar-icon").click(function () {
        $("#dateHelp").empty();
      });

  $("#profile-photo").on('change', function () {
    const imagePreview = $("#profile-img");
    let inputFile = $(this);
    let files = inputFile[0].files;
    let fileName = files[0].name;
    let extension = fileName.substr(fileName.lastIndexOf("."));
    let allowedExtensionsRegx = /(\.jpg|\.jpeg|\.png|\.gif)$/i;
    let isAllowed = allowedExtensionsRegx.test(extension);
    if (isAllowed) {
      $("#profilephotoHelp").empty();
      return true;
    } else {
      $("#profilephotoHelp").html("please upload valid file").addClass("text-danger");
      return false;
    }
  })

  $(".gender").click(function () {
    $("#genderHelp").empty();
  });

  $(".role").click(function () {
    $("#roleHelp").empty();
  });

  $("#update-image-btn").click(function(){
        $("#select-file-btn").click();
  })

  $("#select-file-btn").on('change', function(){
          const imagePreview = $("#profile-img");
          let inputFile = $(this);
          let files = inputFile[0].files;
          let fileName = files[0].name;
          let extension = fileName.substr(fileName.lastIndexOf("."));
          let allowedExtensionsRegx = /(\.jpg|\.jpeg|\.png|\.gif)$/i;
          let isAllowed = allowedExtensionsRegx.test(extension);
          if(isAllowed){
            const selectedFile = files[0];
            console.log(selectedFile);
            if (selectedFile) {
                        // Display the uploaded image
                        const reader = new FileReader();

                        reader.onload = function(e) {
                            let content = e.target.result;
                            console.log(content);
                            $("#profile-img").attr("src", content);
//                            imagePreview.style.display = 'block';
                        };

                        reader.readAsDataURL(selectedFile);
                    }
          $("#profilephotoHelp").empty();
          return true;
          }else{
//            $("#profilephotoHelp").html("please upload valid file").addClass("text-danger");
            alert("please upload valid file");
            return false;
          }
      })

  $("#fname").blur(firstName);
  $("#lname").blur(lastName);
  $("#mobile-number").blur(mobileNumber);
  $("#email-address").blur(emailAddress);
  $("#dob").blur(dateOfBirth);
  $("#security-answer").blur(securityQue);
  $("#password").blur(pwd);
  $("#cnf-password").blur(cnfPassword);
  $("#submit-btn").click(firstName);
  $("#submit-btn").click(lastName);
  $("#submit-btn").click(mobileNumber);
  $("#update-user-btn").click(firstName);
  $("#update-user-btn").click(lastName);
  $("#update-user-btn").click(mobileNumber);
  $("#submit-btn").click(emailAddress);
  $("#forgot-submit-btn").click(emailAddress);
  $("#submit-btn").click(dateOfBirth);
  $("#update-user-btn").click(dateOfBirth);
  $("#submit-btn").click(securityQue);
  $("#forgot-submit-btn").click(securityQue);
  $("#submit-btn").click(pwd);
  $("#reset-submit-btn").click(pwd);
  $("#submit-btn").click(gender);
  $("#update-user-btn").click(gender);
  $("#submit-btn").click(role);
  $("#submit-btn").click(cnfPassword);
  $("#reset-submit-btn").click(cnfPassword);
  $("#submit-btn").click(profilePhoto);


})