<#import "spring.ftl" as s />
<#import "layout.ftl" as layout />
<#import "common.ftl" as c />

<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />

<#assign pageTitle>Login</#assign>

<@layout.layout pageTitle="${pageTitle}">
<style type="text/css">
    body {
        padding-top: 40px;
        padding-bottom: 40px;
        background-color: #f5f5f5;
    }

    .form-signin {
        max-width: 300px;
        padding: 19px 29px 29px;
        margin: 0 auto 20px;
        background-color: #fff;
        border: 1px solid #e5e5e5;
        -webkit-border-radius: 5px;
        -moz-border-radius: 5px;
        border-radius: 5px;
        -webkit-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
        -moz-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
        box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
    }

    .form-signin .form-signin-heading,
    .form-signin .checkbox {
        margin-bottom: 10px;
    }

    .form-signin input[type="text"],
    .form-signin input[type="password"] {
        font-size: 16px;
        height: auto;
        margin-bottom: 15px;
        padding: 7px 9px;
    }

</style>

<div class="container">
    <#if (error??) >
        <div class="alert alert-error">
            Login attempt failed
        </div>
    </#if>
    <form class="form-signin" action='<@s.url "/j_spring_security_check"/>' method='POST'>
        <h2 class="form-signin-heading">Please sign in</h2>
        <input type="text" class="input-block-level" name='j_username' placeholder="Login">
        <input type="password" class="input-block-level" name='j_password' placeholder="Password">
        <label class="checkbox">
            <input type="checkbox" name='_spring_security_remember_me' value="remember-me"> Remember me
        </label>
        <button class="btn btn-large btn-primary" type="submit" name="submit">Sign in</button>
        <a href="<@s.url "/registration" />">Registration</a>
    </form>
    
    
    <form action='<@s.url "/signin/twitter"/>' method='POST'>
    	<button type="submit"><img src="<@s.url "/img/social/twitter.png"/>" /></button>
    </form>
    

</div>
</@layout.layout>