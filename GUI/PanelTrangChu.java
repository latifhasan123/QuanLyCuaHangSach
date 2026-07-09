package gui;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.List;
import java.util.ArrayList;
import DTO.SachDTO;
import BUS.ClientGioHangBUS;
import DAO.SachDAO;

public class PanelTrangChu extends JPanel {
    private DecimalFormat df = new DecimalFormat("#,###");
    private JPanel grid; 
    private ClientMainFrame parent; 
    private SachDAO sachDAO = new SachDAO();
    private List<SachDTO> dsSach;  
    private Color mainPink = Color.decode("#E889A9");
    private Color hoverPink = Color.decode("#D67897");
    private Color lightBg = Color.decode("#F8F9FA");
    private Color textDark = Color.decode("#2D3436");
    private AnimatedBannerPanel banner;

    public PanelTrangChu(ClientMainFrame parent) {
        this.parent = parent;
        setLayout(new BorderLayout());
        setBackground(lightBg);
        
        JPanel mainScrollContent = new JPanel(new BorderLayout());
        mainScrollContent.setBackground(lightBg);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(lightBg);

        banner = new AnimatedBannerPanel();
        topPanel.add(banner, BorderLayout.NORTH);

        JPanel pnlTitle = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlTitle.setBackground(lightBg);
        pnlTitle.setBorder(new EmptyBorder(20, 40, 0, 40));
        
        JLabel notableProducts = new JLabel("Sản phẩm nổi bật");
        notableProducts.setFont(new Font("Segoe UI", Font.BOLD, 26));
        notableProducts.setForeground(textDark);
        pnlTitle.add(notableProducts);
        
        topPanel.add(pnlTitle, BorderLayout.CENTER);
        
        mainScrollContent.add(topPanel, BorderLayout.NORTH);

        grid = new JPanel(new GridLayout(0, 4, 30, 40));
        grid.setBorder(new EmptyBorder(10, 40, 50, 40));
        grid.setBackground(lightBg);

        JPanel gridWrapper = new JPanel(new BorderLayout());
        gridWrapper.setBackground(lightBg);
        gridWrapper.add(grid, BorderLayout.NORTH);

        mainScrollContent.add(gridWrapper, BorderLayout.CENTER);

        JScrollPane scroll = new JScrollPane(mainScrollContent);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(20);
        scroll.setBackground(lightBg);
        scroll.getViewport().setBackground(lightBg);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        scroll.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = mainPink;
                this.trackColor = lightBg;
            }
            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createZeroButton();
            }
            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createZeroButton();
            }
            private JButton createZeroButton() {
                JButton jbutton = new JButton();
                jbutton.setPreferredSize(new Dimension(0, 0));
                jbutton.setMinimumSize(new Dimension(0, 0));
                jbutton.setMaximumSize(new Dimension(0, 0));
                return jbutton;
            }
            @Override
            protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
                if (thumbBounds.isEmpty() || !scrollbar.isEnabled()) return;
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(thumbColor);
                g2.fillRoundRect(thumbBounds.x + 2, thumbBounds.y + 2, thumbBounds.width - 4, thumbBounds.height - 4, 10, 10);
            }
            @Override
            protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(trackColor);
                g2.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
            }
        });

        add(scroll, BorderLayout.CENTER);
        loadSach();
    }

    public void loadSach() {
        loadSach("", ""); 
    }

    public void loadSach(String keyword, String category) {
        grid.removeAll(); 
        dsSach = sachDAO.getAll(); 
        
        java.util.List<SachDTO> filteredList = new java.util.ArrayList<>();
        for (SachDTO s : dsSach) {
            boolean matchKeyword = keyword.isEmpty() || s.getTenSach().toLowerCase().contains(keyword.toLowerCase());
            boolean matchCategory = category.isEmpty() || (s.getTenLoai() != null && s.getTenLoai().equalsIgnoreCase(category));
            
            if (matchKeyword && matchCategory) {
                filteredList.add(s);
            }
        }
    
        if (filteredList.isEmpty()) {
            grid.setLayout(new FlowLayout());
            JLabel lblEmpty = new JLabel("Không tìm thấy sản phẩm phù hợp.");
            lblEmpty.setFont(new Font("Segoe UI", Font.ITALIC, 18));
            grid.add(lblEmpty);
        } else {
            grid.setLayout(new GridLayout(0, 4, 30, 40));
            for (SachDTO sach : filteredList) {
                grid.add(createCard(sach));
            }
        }
        
        grid.revalidate();
        grid.repaint();
    }

    private JPanel createCard(DTO.SachDTO sach) {
        JPanel card = new JPanel(new BorderLayout()){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 25, 25); 
                g2.setColor(Color.decode("#E8E8E8")); 
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 25, 25);
            }
        };
        card.setOpaque(false);
        card.setPreferredSize(new Dimension(280, 440)); 
        card.setBorder(new EmptyBorder(20, 15, 20, 15));
        
        JPanel imgWrapper = new JPanel(new GridBagLayout()); 
        imgWrapper.setOpaque(false);
        
        JLabel img = new JLabel("", SwingConstants.CENTER);
        try {
            String tenAnh = sach.getHinhAnh() != null ? sach.getHinhAnh().trim() : "";
            java.net.URL imgURL = getClass().getResource("/images/" + tenAnh);
            
            if (imgURL != null) {
                ImageIcon icon = new ImageIcon(imgURL);
                Image scaledImg = icon.getImage().getScaledInstance(170, 240, Image.SCALE_SMOOTH);
                img.setIcon(new ImageIcon(scaledImg));
            } else {
                img.setText("Không tìm thấy ảnh"); 
            }
        } catch (Exception e) {
            img.setText("Lỗi load ảnh"); 
        }
        
        img.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.decode("#F0F0F0"), 1),
            BorderFactory.createEmptyBorder(2, 2, 2, 2)
        ));
        imgWrapper.add(img);

        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setOpaque(false);

        JLabel title = new JLabel("<html><div style='text-align: center; width: 180px;'>" + sach.getTenSach() + "</div></html>");
        title.setFont(new Font("Segoe UI", Font.BOLD, 17));
        title.setForeground(Color.decode("#2D3436"));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        Dimension titleSize = new Dimension(220, 50);
        title.setPreferredSize(titleSize);
        title.setMinimumSize(titleSize);
        title.setMaximumSize(titleSize);

        JLabel price = new JLabel(df.format(sach.getGiaBan()) + " đ");
        price.setFont(new Font("Segoe UI", Font.BOLD, 19));
        price.setForeground(Color.decode("#E889A9"));
        price.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btn = new JButton("Thêm vào giỏ") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isRollover()) {
                    g2.setColor(Color.decode("#D67897")); 
                } else {
                    g2.setColor(Color.decode("#E889A9"));
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                super.paintComponent(g);
            }
        };
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setForeground(Color.WHITE);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(180, 40));

        btn.addActionListener(e -> {
            if (BUS.KhachHangBUS.currentCustomer == null) {
                parent.showLoginWarning(); 
                return;
            }
            
            BUS.ClientGioHangBUS bus = new BUS.ClientGioHangBUS();
            bus.themVaoGio(sach, 1);
            showToast(); 
        });
        
        info.add(Box.createVerticalStrut(15));
        info.add(title);
        info.add(Box.createVerticalStrut(5));
        info.add(price);
        info.add(Box.createVerticalGlue()); 
        info.add(btn);

        card.add(imgWrapper, BorderLayout.CENTER);
        card.add(info, BorderLayout.SOUTH);

        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                parent.switchPanel(new PanelChiTietSach(sach));
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                card.setCursor(new Cursor(Cursor.HAND_CURSOR));
                card.setBorder(new CompoundBorder(
                    new LineBorder(Color.decode("#E889A9"), 2, true),
                    new EmptyBorder(18, 13, 18, 13) 
                ));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                card.setBorder(new EmptyBorder(20, 15, 20, 15));
            }
        });
        return card;
    }

    private void showToast() {
        Window window = SwingUtilities.getWindowAncestor(this);
        if (window == null) return;

        final JDialog toast = new JDialog((Frame) window, false);
        toast.setUndecorated(true);
        toast.setBackground(new Color(0, 0, 0, 0)); 

        JPanel pnlToast = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.decode("#34C759")); 
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 35, 35); 
                super.paintComponent(g);
            }
        };
        pnlToast.setOpaque(false);
        pnlToast.setBorder(new EmptyBorder(12, 30, 12, 30)); 

        JLabel msg = new JLabel("Thêm vào giỏ thành công!", SwingConstants.CENTER);
        msg.setForeground(Color.WHITE);
        msg.setFont(new Font("Segoe UI", Font.BOLD, 15));
        
        pnlToast.add(msg, BorderLayout.CENTER);
        toast.add(pnlToast);
        toast.pack(); 
        
        toast.setLocationRelativeTo(window);
        
        toast.setVisible(true);
        new Timer(1500, e -> toast.dispose()).start();
    }

    private class AnimatedBannerPanel extends JPanel {
        private List<Image> images = new ArrayList<>();
        private int currentIndex = 0;
        private Image currentScaledImage;
        private Image nextScaledImage;
        private double animationProgress = 1.0; 
        private Timer animationTimer;
        private Timer autoAdvanceTimer;
        private boolean isAnimating = false;
        private boolean isNext = true;

        public AnimatedBannerPanel() {
            setPreferredSize(new Dimension(0, 220)); 
            setLayout(null); 
            setOpaque(false);

            loadImages();
            if (!images.isEmpty()) {
                scaleImage(images.get(currentIndex), true);
            }

            JButton btnPrev = createNavButton(true);
            add(btnPrev);

            JButton btnNext = createNavButton(false);
            add(btnNext);
            
            addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    if (getWidth() > 0) {
                        btnNext.setBounds(getWidth() - 200, (getHeight() - 60) / 2 + 5, 40, 60);
                        btnPrev.setBounds(160, (getHeight() - 60) / 2 + 5, 40, 60);
                        if (!images.isEmpty()) {
                            scaleImage(images.get(currentIndex), true);
                        }
                    }
                }
            });

            btnPrev.addActionListener(e -> previousImage());
            btnNext.addActionListener(e -> nextImage());

            autoAdvanceTimer = new Timer(3000, e -> nextImage());
            autoAdvanceTimer.start();
        }

        private void loadImages() {
            String[] imageNames = {"banner1.jpeg", "banner2.jpeg", "banner3.jpeg"};
            for (String name : imageNames) {
                try {
                    java.net.URL imgURL = getClass().getResource("/images/" + name);
                    if (imgURL != null) {
                        images.add(new ImageIcon(imgURL).getImage());
                    }
                } catch (Exception e) {}
            }
        }

        private void scaleImage(Image img, boolean isCurrent) {
            int panelW = getWidth() - 300;
            int panelH = getHeight() - 20;
            if (panelH <= 0 || panelW <= 0) return;
            
            if (isCurrent) {
                currentScaledImage = img.getScaledInstance(panelW, panelH, Image.SCALE_SMOOTH);
            } else {
                nextScaledImage = img.getScaledInstance(panelW, panelH, Image.SCALE_SMOOTH);
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (currentScaledImage == null && nextScaledImage == null) return;

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            Shape clip = new java.awt.geom.RoundRectangle2D.Float(150, 10, getWidth() - 300, getHeight() - 20, 20, 20);
            g2.setClip(clip);

            int currentPanelW = getWidth() - 300;
            int drawY = 10;

            if (isAnimating) {
                int shiftX = (int)(currentPanelW * animationProgress);
                if (isNext) {
                    g2.drawImage(currentScaledImage, 150 - shiftX, drawY, this);
                    if (nextScaledImage != null) {
                        g2.drawImage(nextScaledImage, 150 + currentPanelW - shiftX, drawY, this);
                    }
                } else {
                    g2.drawImage(currentScaledImage, 150 + shiftX, drawY, this);
                    if (nextScaledImage != null) {
                        g2.drawImage(nextScaledImage, 150 - currentPanelW + shiftX, drawY, this);
                    }
                }
            } else {
                g2.drawImage(currentScaledImage, 150, drawY, this);
            }
            g2.setClip(null);
        }

        private void nextImage() {
            if (isAnimating || images.size() < 2) return;
            isAnimating = true;
            isNext = true;
            int nextIdx = (currentIndex + 1) % images.size();
            scaleImage(images.get(nextIdx), false);
            startAnimation();
        }

        private void previousImage() {
            if (isAnimating || images.size() < 2) return;
            isAnimating = true;
            isNext = false;
            int prevIdx = (currentIndex - 1 + images.size()) % images.size();
            scaleImage(images.get(prevIdx), false);
            startAnimation();
        }

        private void startAnimation() {
            animationProgress = 0.0;
            autoAdvanceTimer.restart();
            if (animationTimer != null) animationTimer.stop();
            
            animationTimer = new Timer(15, e -> {
                animationProgress += 0.05; 
                if (animationProgress >= 1.0) {
                    animationProgress = 1.0;
                    animationTimer.stop();
                    currentScaledImage = nextScaledImage;
                    nextScaledImage = null;
                    currentIndex = isNext ? (currentIndex + 1) % images.size() : (currentIndex - 1 + images.size()) % images.size();
                    isAnimating = false;
                }
                repaint();
            });
            animationTimer.start();
        }

        private JButton createNavButton(boolean isLeft) {
            JButton btn = new JButton() {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(new Color(0, 0, 0, 80));
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                    g2.setColor(Color.WHITE);
                    g2.setStroke(new BasicStroke(3));
                    int w = getWidth(), h = getHeight();
                    if (isLeft) {
                        g2.drawLine(w * 2/3, h/4, w/3, h/2);
                        g2.drawLine(w/3, h/2, w * 2/3, h * 3/4);
                    } else {
                        g2.drawLine(w/3, h/4, w * 2/3, h/2);
                        g2.drawLine(w * 2/3, h/2, w/3, h * 3/4);
                    }
                    super.paintComponent(g);
                }
            };
            btn.setContentAreaFilled(false);
            btn.setBorderPainted(false);
            btn.setFocusPainted(false);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.setPreferredSize(new Dimension(40, 60));
            return btn;
        }
    }
}