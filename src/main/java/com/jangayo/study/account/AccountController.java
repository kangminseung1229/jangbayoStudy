package com.jangayo.study.account;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {


    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final AccountRoleRepository accountRoleRepository;
    private final SignUpFormValidator signUpFormValidator;

    @InitBinder("signUpForm")
    public void InitBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(signUpFormValidator);
    }

    //회원가입창
    @GetMapping("/sign-up")
    public String signUpPage( Model model){
        model.addAttribute("signUpForm", new SignUpForm());
        return "login/sign-up";
    }

    //회원가입처리
    @PostMapping("/sign-up")
    public String signUp(@Valid SignUpForm signUpForm, Errors errors){


        if (errors.hasErrors()) {
            return "login/sign-up";
        }

        accountService.signUp(signUpForm);  
        return "redirect:/";
    }

    //권한확인
    @GetMapping("/principal")
    public String principal(){  
        return "login/principal";
    }

    //권한편집 페이지
    @GetMapping("/principal-modify")
    public String principalModify(@RequestParam(required = false) String searchText, Model model){


        if (searchText != null) {
            Account searchAccount = accountRepository.findByNickname(searchText);

            if (searchAccount == null) {
                model.addAttribute("message", "일치하는 아이디가 없습니다." );
            } else{
                model.addAttribute("auth", searchAccount.getRoles());
            }
        } else {
            model.addAttribute("message", "아이디를 넣어주세요!");
        }


        model.addAttribute("allAuth", accountRoleRepository.findAll());
        model.addAttribute("searchText", searchText);

        return "account/principal-modify";
    }

    //권한 수정
    @PostMapping("/principal-modify/{nickname}")
    @ResponseBody
    public Account principalUpdate( @RequestBody List<Long> authorities, @PathVariable String nickname){
        return accountService.principalUpdate(authorities, nickname);
    }

    @GetMapping("/check-email-token")
    public String checkEmailToken(@RequestParam String token, @RequestParam String email, Model model){

        Account newAccount = accountRepository.findByEmail(email);

        // 이메일 또는 토큰 불일치
        if (newAccount == null || !newAccount.isValidToken(token)) {
            model.addAttribute("error", "fail");
        } else{
            accountService.completeSignUp(newAccount);
            model.addAttribute("nickname", newAccount.getNickname());
            model.addAttribute("cnt", accountRepository.count());
        }

        return "account/check-email-success";

    }

    //이메일 재전송 페이지
    @GetMapping("/check-email")
    public String checkEmail(){
        return "account/check-email";
    }

    // 이메일 재전송
    @GetMapping("/check-email-resend")
    public String checkEmailResend(@CurrentUser Account account, RedirectAttributes attributes){

        if (!account.canSendConfirmEmail()) {
            attributes.addFlashAttribute("mailResendMessage", "인증 메일은 1시간에 한 번씩 보낼 수 있습니다.");
        } else{
            accountService.mailMessage(account);
            attributes.addFlashAttribute("mailResendMessage", "인증 메일을 재발송 했습니다!");
        }
        
        return "redirect:/account/check-email";
    }



    
}



