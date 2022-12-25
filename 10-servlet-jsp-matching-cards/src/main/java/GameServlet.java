import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/game")
public class GameServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String [] sizes = request.getParameterValues("sizes");

        int horizontal = Integer.parseInt(sizes[0]);
        int vertical = Integer.parseInt(sizes[1]);

        request.setAttribute("horizontal", horizontal);
        request.setAttribute("vertical", vertical);

        getServletContext().
                getRequestDispatcher("/game.jsp").forward(request, response);
    }
}