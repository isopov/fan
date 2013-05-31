<#import "spring.ftl" as spring />
<#import "layout.ftl" as layout />
<#import "common.ftl" as c />

<#assign pageTitle><@spring.message "simple.register" /></#assign>

<@layout.layout pageTitle="${pageTitle}">

<script src="<@spring.url "/js/jquery.validate-1.10.0.js"/>" type="text/javascript"></script>
<script type="text/javascript">

    $(document).ready(function(){
        $("#registrationForm").validate();
    });
</script>

<form action='<@spring.url "/registration"/>' method='POST' id="registrationForm">
    <@spring.formInput "registrationData.userName" "class='required input-block-level' minlength='4' maxlength='30' placeholder='Username'" />
    <@spring.showErrors "<br>"/>
    <br />
    <#--<input type="text" class="input-block-level" name='userName' placeholder="Username">-->

    <@spring.formInput "registrationData.visibleName" "class='required input-block-level' minlength='5' maxlength='30' placeholder='Visible Name'" />
    <@spring.showErrors "<br>"/>
    <br />
    <!--<input type="text" class="input-block-level" name='visibleName' placeholder="Visible Name">-->

    <@spring.formInput "registrationData.email" "class='required email input-block-level' minlength='5' maxlength='30' placeholder='Email'" "email" />
    <@spring.showErrors "<br>"/>
    <br />
    <!--<input type="email" class="input-block-level" name='email' placeholder="Email">-->

    <@spring.formPasswordInput "registrationData.password" "class='required input-block-level' minlength='3' maxlength='30' placeholder='Password'" />
    <@spring.showErrors "<br>"/>
    <br />
    <!--<input type="password" class="input-block-level" name='password' placeholder="Password">-->

    <@spring.formPasswordInput "registrationData.password2" "equalto='#password' class='required input-block-level' minlength='3' maxlength='30' placeholder='Repeat password' " />
    <@spring.showErrors "<br>"/>
    <br />
    <!--<input type="password" class="input-block-level" name='password2' placeholder="Repeat password">-->

    <button class="btn btn-large btn-primary" type="submit" name="submit"><@spring.message "simple.register" /></button>
</form>
</@layout.layout>
