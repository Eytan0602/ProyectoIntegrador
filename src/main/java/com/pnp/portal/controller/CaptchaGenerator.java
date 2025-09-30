package com.pnp.portal.controller;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

public class CaptchaGenerator extends HttpServlet {

    private static final int WIDTH = 120;
    private static final int HEIGHT = 40;
    private static final int CODE_LENGTH = 6;
    private static final String CHARS = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedImage bufferedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics g = bufferedImage.getGraphics();

        g.setColor(new Color(240, 240, 240));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        Random rand = new Random();
        g.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i < 15; i++) {
            int x1 = rand.nextInt(WIDTH);
            int y1 = rand.nextInt(HEIGHT);
            int x2 = rand.nextInt(WIDTH);
            int y2 = rand.nextInt(HEIGHT);
            g.drawLine(x1, y1, x2, y2);
        }

        StringBuilder captchaStrBuilder = new StringBuilder();
        g.setFont(new Font("Arial", Font.BOLD, 28));
        for (int i = 0; i < CODE_LENGTH; i++) {
            char c = CHARS.charAt(rand.nextInt(CHARS.length()));
            captchaStrBuilder.append(c);
            g.setColor(new Color(rand.nextInt(100), rand.nextInt(100) + 50, rand.nextInt(100)));
            int x = 15 + i * 18;
            int y = 28 + rand.nextInt(5);
            g.drawString(String.valueOf(c), x, y);
        }

        HttpSession session = request.getSession();
        session.setAttribute("captcha", captchaStrBuilder.toString());

        g.dispose();

        response.setContentType("image/png");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        ImageIO.write(bufferedImage, "png", response.getOutputStream());
    }
}
