// 代码生成时间: 2025-09-21 16:40:31
package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import play.libs.Json;
import com.fasterxml.jackson.databind.JsonNode;
import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import services.PaymentService;
import models.Payment;

/**
 * Controller class for handling payment process.
 */
public class PaymentProcessController extends Controller {

    private final PaymentService paymentService;

    /**
     * Constructor injecting the PaymentService.
     * @param paymentService The PaymentService instance.
     */
    @Inject
    public PaymentProcessController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    /**
     * Handles HTTP POST request for payment processing.
     * @return A CompletionStage of Result containing the payment result.
     */
    public CompletionStage<Result> processPayment() {
        JsonNode requestJson = request().body().asJson();
        if (requestJson == null) {
            return CompletableFuture.completedFuture(
                badRequest("Please provide a valid JSON body.")
            );
        }

        Payment payment = Json.fromJson(requestJson, Payment.class);
        if (payment == null) {
            return CompletableFuture.completedFuture(
                badRequest("Invalid payment data.")
            );
        }

        return paymentService.processPayment(payment).thenApplyAsync(paymentResult -> {
            if (paymentResult.isSuccess()) {
                return ok(Json.toJson(paymentResult));
            } else {
                return badRequest(Json.toJson(paymentResult));
            }
        }).exceptionally(ex -> {
            return internalServerError(ex.getMessage());
        });
    }
}
