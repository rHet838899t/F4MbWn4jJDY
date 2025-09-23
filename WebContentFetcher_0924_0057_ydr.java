// 代码生成时间: 2025-09-24 00:57:44
import play.mvc.*;
import play.libs.F.Promise;
import play.libs.ws.WS;
import play.libs.ws.WSRequest;
import play.libs.ws.WSResponse;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.CompletableFuture;

public class WebContentFetcher extends Controller {

    // 异步抓取网页内容
    public CompletionStage<Result> fetchWebContent(String url) {
        return WS.url(url).get()
                .thenCompose(this::handleResponse)
                .thenApply(this::renderHtml);
    }

    // 处理响应
    private Promise<WSResponse> handleResponse(WSResponse response) {
        if (response.getStatus() == 200) {
            return Promise.promise(response);
        } else {
            // 响应状态码非200时，抛出异常
            throw new RuntimeException("Failed to fetch content, status: " + response.getStatus());
        }
    }

    // 渲染HTML内容
    private Result renderHtml(WSResponse response) {
        try {
            // 将网页内容作为字符串返回
            String content = response.getBody();
            return ok(content);
        } catch (Exception e) {
            // 异常处理，返回错误页面
            return internalServerError("Error fetching content: " + e.getMessage());
        }
    }
}
