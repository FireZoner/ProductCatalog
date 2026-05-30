package main.feedback.web;

import jakarta.validation.Valid;
import main.feedback.domain.FeedbackRequest;
import main.feedback.service.FeedbackService;
import main.product.domain.Product;
import main.product.service.ProductService;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 *
 * @author zubbo
 */
@Controller
@RequestMapping("/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;
    private final ProductService productService;

    public FeedbackController(
            FeedbackService feedbackService,
            ProductService productService
    ) {
        this.feedbackService = feedbackService;
        this.productService = productService;
    }

    @GetMapping
    public String showFeedbackForm(
            @RequestParam(required = false) Long productId,
            Model model
    ) {
        CreateFeedbackRequest request = new CreateFeedbackRequest();
        request.setProductId(productId);

        addFormAttributes(model, request, productId);

        return "feedback/form";
    }
    
    @GetMapping("/history")
    public String showFeedbackHistory(
            Authentication authentication,
            Model model
    ) {
        List<FeedbackRequest> feedbackRequests =
                feedbackService.findCurrentUserFeedbackRequests(authentication.getName());

        model.addAttribute("feedbackRequests", feedbackRequests);

        return "feedback/history";
    }

    @PostMapping
    public String createFeedback(
            @Valid @ModelAttribute("feedbackRequest") CreateFeedbackRequest request,
            BindingResult bindingResult,
            Authentication authentication,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            addFormAttributes(model, request, request.getProductId());
            return "feedback/form";
        }

        String userEmail = authentication.getName();

        FeedbackRequest savedRequest = feedbackService.createFeedback(request, userEmail);

        return "redirect:/feedback/result/" + savedRequest.getId();
    }

    @GetMapping("/result/{id}")
    public String showFeedbackResult(@PathVariable Long id, Authentication authentication, Model model) {
        FeedbackRequest feedbackRequest =
                feedbackService.findFeedbackRequestById(id, authentication.getName());
        model.addAttribute("feedbackRequest", feedbackRequest);
        return "feedback/result";
    }

    private void addFormAttributes(
            Model model,
            CreateFeedbackRequest request,
            Long productId
    ) {
        model.addAttribute("feedbackRequest", request);

        if (productId != null) {
            Product product = productService.findProductById(productId);
            model.addAttribute("product", product);
        }
    }
}
