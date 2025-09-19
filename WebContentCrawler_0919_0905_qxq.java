// 代码生成时间: 2025-09-19 09:05:05
package crawler;

import play.libs.ws.WS;
import play.libs.ws.WSResponse;
import play.mvc.Controller;
import play.mvc.Result;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class WebContentCrawler extends Controller {

    private static final Executor executor = play.libs.concurrent.CustomExecutionContext.executor("webContentCrawlerPool");

    // Method to fetch and return web content
    public CompletionStage<Result> crawl(String url) {
        return WS.url(url)
                .get()
                .thenApplyAsync(response -> {
                    try {
                        // Check if the response is successful
                        if (response.getStatus() == 200) {
                            // Return the content of the response
                            return ok(response.asJson());
                        } else {
                            // Return an error message if the status is not 200
                            return badRequest("Failed to retrieve content: " + response.getStatus());
                        }
                    } catch (Exception e) {
                        // Handle any exceptions that occur during the request
                        return internalServerError("An error occurred: " + e.getMessage());
                    }
                }, executor);
    }

    // Action method to handle the HTTP request
    public Result crawlContent(String url) {
        // Call the crawl method and return the result
        return (Result) crawl(url).toCompletableFuture().join();
    }
}
