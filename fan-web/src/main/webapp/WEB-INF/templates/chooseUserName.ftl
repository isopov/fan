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

<form action='<@spring.url "/signup"/>' method='POST' id="registrationForm">
    <@spring.formInput "signupData.userName" "class='required input-block-level' minlength='4' maxlength='30' placeholder='Username'" />
    <@spring.showErrors "<br>"/>
    <br />

	<@spring.formInput "signupData.visibleName" "class='required input-block-level' minlength='5' maxlength='30' placeholder='Visible Name'" />
    <@spring.showErrors "<br>"/>
    <br />
    <!--<input type="text" class="input-block-level" name='visibleName' placeholder="Visible Name">-->


    <button class="btn btn-large btn-primary" type="submit" name="submit"><@spring.message "simple.register" /></button>
</form>
</@layout.layout>
