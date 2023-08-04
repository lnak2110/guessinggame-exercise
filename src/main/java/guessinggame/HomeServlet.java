package guessinggame;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "HomeServlet", urlPatterns = "/home")
public class HomeServlet extends HttpServlet {
    private Map<String, Integer> leaderboard;
    private String player;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (leaderboard != null) {
            this.getServletConfig().getServletContext().setAttribute("leaderboard", leaderboard);
            req.setAttribute("leaderboardFilter",
                    leaderboard.entrySet().stream().filter(p -> p.getValue() > 0)
                            .sorted(Map.Entry.<String, Integer>comparingByValue()
                                    .thenComparing(Map.Entry.comparingByKey()))
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                    (oldValue, newValue) -> oldValue, LinkedHashMap::new)));
        }
        req.getRequestDispatcher("jsp/home.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name = req.getParameter("name");
        String context = req.getContextPath();

        leaderboard.putIfAbsent(name, 0);

        this.getServletConfig().getServletContext().setAttribute("name", name);
        this.getServletConfig().getServletContext().setAttribute("guessCount",
                leaderboard.get(name));

        resp.sendRedirect(context + "/game");
    }

    @Override
    public void init() throws ServletException {
        leaderboard = new LinkedHashMap<String, Integer>();
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }
}
