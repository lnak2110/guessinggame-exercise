package guessinggame;

import java.io.IOException;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "GameServlet", urlPatterns = "/game")
public class GameServlet extends HttpServlet {
    private int num;
    private int count = 0;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (this.getServletConfig().getServletContext().getAttribute("name") == null) {
            String context = req.getContextPath();
            resp.sendRedirect(context + "/home");
            return;
        }
        count = 0;
        num = new Random().nextInt(1000) + 1;
        req.getRequestDispatcher("jsp/game.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        count++;
        int guessNum = Integer.valueOf(req.getParameter("guessNum"));
        req.setAttribute("guessNum", guessNum);

        String message = "";
        boolean win = false;

        if (guessNum < 1 || guessNum > 1000) {
            message = "Please enter a number between 1 and 1000";
        }

        if (guessNum == num) {
            win = true;
            message = "You guessed the correct number: ".concat(String.valueOf(num)).concat(" (in ")
                    .concat(String.valueOf(count)).concat(count > 1 ? " guesses)" : " guess)");

            String context = req.getContextPath();
            ServletContext servletContext = this.getServletConfig().getServletContext();
            Map<String, Integer> leaderboard = (Map<String, Integer>) servletContext
                    .getAttribute("leaderboard");

            if (leaderboard == null) {
                resp.sendRedirect(context + "home");
                return;
            }

            String name = (String) servletContext.getAttribute("name");

            if (leaderboard.get(name) == 0 || count < leaderboard.get(name)) {
                leaderboard.put(name, count);
                servletContext.setAttribute("guessCount", count);
            }
        } else if (guessNum < num) {
            message = guessNum + " is lower than the actual number";
        } else {
            message = guessNum + " is higher than the actual number";
        }

        req.setAttribute("message", message);
        req.setAttribute("win", win);

        req.getRequestDispatcher("jsp/game.jsp").forward(req, resp);
    }
}
