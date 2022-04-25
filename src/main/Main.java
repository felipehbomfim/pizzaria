/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Pedido.Pedido;
import Pedido.PedidoDao;
import Pedido.PedidoDaoImpl;
import cliente.Cliente;
import cliente.ClienteDao;
import cliente.ClienteDaoImpl;
import cliente.ClienteForm;
import connectionFactory.ConnectionFactory;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import pizza.PizzaForm;
import sabor.Sabor;
import sabor.SaborDao;
import sabor.SaborDaoImpl;
import tipo.TipoDao;
import tipo.TipoDaoImpl;

/**
 *
 * @author lipe1
 */
public class Main extends javax.swing.JFrame {

    /**
     * Creates new form java
     */
    private DefaultTableModel model;
    private DefaultTableModel modelSabor;
    private DefaultTableModel modelPedido;
    private float s;
    private float e;
    private float p;
    
    public Main() throws ClassNotFoundException {
        initComponents();
        this.model = new DefaultTableModel();
        tabela.setModel(this.model);
        this.model.addColumn("ID");
        this.model.addColumn("NOME");
        this.model.addColumn("SOBRENOME");
        this.model.addColumn("TELEFONE");
        this.model.addColumn("ENDEREÇO");
        lbErro.setVisible(false);
        imprimeTodos();

        this.modelSabor = new DefaultTableModel();
        tabelaSabores.setModel(this.modelSabor);
        this.modelSabor.addColumn("ID");
        this.modelSabor.addColumn("SABOR");
        this.modelSabor.addColumn("TIPO");
        lbErroSabor.setVisible(false);
        lbInvalido.setVisible(false);
        rbSimples.setSelected(true);
        imprimeTodosSabores();
        
        imprimeValores();
        
        lbErroPedidos.setVisible(false);
        
        this.modelPedido = new DefaultTableModel();
        tabelaPedidos.setModel(this.modelPedido);
        this.modelPedido.addColumn("ID");
        this.modelPedido.addColumn("CLIENTE");
        this.modelPedido.addColumn("STATUS");
        this.modelPedido.addColumn("QTD. ITENS");
        this.modelPedido.addColumn("VALOR TOTAL");
        imprimeTodosPedidos();
    }
    
    public void imprimeValores() throws ClassNotFoundException {
        Connection con;
        try {
            con = ConnectionFactory.getConnection("jdbc:mysql://localhost:3307/db_pizza", "root", "", ConnectionFactory.MYSQL);

            TipoDao t = new TipoDaoImpl(con);

            this.s = t.getPrecoSimples();
            this.e = t.getPrecoEspecial();
            this.p = t.getPrecoPremium();
            
            if(!tfSimples.getText().equals(Float.toString(this.s)))
                tfSimples.setText(Float.toString(this.s));
            if(!tfEspecial.getText().equals(Float.toString(this.e)))
                tfEspecial.setText(Float.toString(this.e));
            if(!tfPremium.getText().equals(Float.toString(this.p)))
                tfPremium.setText(Float.toString(this.p));

            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
    public void imprimeTodosSabores() {
        apagaTabelaSabores();
        Connection con;
        try {
            con = ConnectionFactory.getConnection("jdbc:mysql://localhost:3307/db_pizza", "root", "", ConnectionFactory.MYSQL);
            SaborDao sDao = new SaborDaoImpl(con);
            String str=null;
            for (Sabor s : sDao.buscarTodos(campo_busca_sabores.getText())) {
                this.modelSabor.addRow(new Object[]{s.getId(), s.getNome(), s.getNomeTipo()});
            }
            con.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClienteForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ClienteForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
    public void imprimeTodosPedidos() {
        apagaTabelaPedidos();
        Connection con;
        try {
            con = ConnectionFactory.getConnection("jdbc:mysql://localhost:3307/db_pizza", "root", "", ConnectionFactory.MYSQL);
            PedidoDao pDao = new PedidoDaoImpl(con);
            String str=null;
            for (Pedido p : pDao.buscarTodosPedidos(buscaPedidos.getText())) {
                Locale localeBR = new Locale( "pt", "BR" );  
                NumberFormat dinheiroBR = NumberFormat.getCurrencyInstance(localeBR);  
                Double currentTotal = p.getValorTotal();
                this.modelPedido.addRow(new Object[]{p.getId(), p.getNomeCliente(), p.getNomeStatus(), p.getQtdItens(), dinheiroBR.format(currentTotal)});
            }
            con.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClienteForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ClienteForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
       
    public void imprimeTodos() {
        apagaTabela();
        Connection con;
        try {
            con = ConnectionFactory.getConnection("jdbc:mysql://localhost:3307/db_pizza", "root", "", ConnectionFactory.MYSQL);
            ClienteDao cDao = new ClienteDaoImpl(con);
            for (Cliente c : cDao.buscarTodos(campo_busca.getText())) {
                this.model.addRow(new Object[]{c.getId(), c.getNome(), c.getSobrenome(), c.getTelefone(), c.getEndereco()});
            }
            con.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClienteForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ClienteForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void apagaTabela() {
        int rowCount = this.model.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            this.model.removeRow(i);
        }
    }
    
    public void apagaTabelaSabores() {
        int rowCount = this.modelSabor.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            this.modelSabor.removeRow(i);
        }
    }
    
    public void apagaTabelaPedidos() {
        int rowCount = this.modelPedido.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            this.modelPedido.removeRow(i);
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        campo_busca = new javax.swing.JTextField();
        btBuscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        btAdicionarCliente = new javax.swing.JButton();
        btEditarCliente = new javax.swing.JButton();
        btExcluirCliente = new javax.swing.JButton();
        lbErro = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        AddPedido = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabelaPedidos = new javax.swing.JTable();
        ListarItens = new javax.swing.JButton();
        Status3 = new javax.swing.JButton();
        lbErroPedidos = new javax.swing.JLabel();
        buscar_pedidos = new javax.swing.JButton();
        buscaPedidos = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        Status2 = new javax.swing.JButton();
        Status1 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        campo_busca_sabores = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelaSabores = new javax.swing.JTable();
        btBuscarSabores = new javax.swing.JButton();
        btExcluirCliente1 = new javax.swing.JButton();
        lbErroSabor = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        tfInserir = new javax.swing.JTextField();
        btSalvarSabor = new javax.swing.JButton();
        lbInvalido = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        rbSimples = new javax.swing.JRadioButton();
        rbEspecial = new javax.swing.JRadioButton();
        rbPremium = new javax.swing.JRadioButton();
        jLabel14 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        tfSimples = new javax.swing.JTextField();
        tfEspecial = new javax.swing.JTextField();
        tfPremium = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        SalvarValores = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(null);

        campo_busca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campo_buscaActionPerformed(evt);
            }
        });
        jPanel1.add(campo_busca);
        campo_busca.setBounds(25, 25, 546, 23);

        btBuscar.setText("Buscar");
        btBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btBuscarActionPerformed(evt);
            }
        });
        jPanel1.add(btBuscar);
        btBuscar.setBounds(631, 25, 110, 23);

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NOME ", "SOBRENOME", "TELEFONE"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tabela);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(25, 60, 546, 315);

        btAdicionarCliente.setText("Adicionar");
        btAdicionarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAdicionarClienteActionPerformed(evt);
            }
        });
        jPanel1.add(btAdicionarCliente);
        btAdicionarCliente.setBounds(631, 60, 110, 23);

        btEditarCliente.setText("Editar");
        btEditarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEditarClienteActionPerformed(evt);
            }
        });
        jPanel1.add(btEditarCliente);
        btEditarCliente.setBounds(631, 94, 110, 23);

        btExcluirCliente.setText("Excluir");
        btExcluirCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btExcluirClienteActionPerformed(evt);
            }
        });
        jPanel1.add(btExcluirCliente);
        btExcluirCliente.setBounds(631, 128, 110, 23);

        lbErro.setForeground(new java.awt.Color(255, 51, 51));
        lbErro.setText("POR FAVOR, SELECIONE UM CLIENTE CLICANDO NA LINHA.");
        jPanel1.add(lbErro);
        lbErro.setBounds(110, 380, 460, 14);

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Digite o que deseja buscar:");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(25, 6, 303, 14);

        AddPedido.setText("Novo Pedido");
        AddPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddPedidoActionPerformed(evt);
            }
        });
        jPanel1.add(AddPedido);
        AddPedido.setBounds(631, 162, 110, 23);

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/pizzaback.jpg"))); // NOI18N
        jPanel1.add(jLabel9);
        jLabel9.setBounds(0, 0, 810, 440);

        jTabbedPane1.addTab("Clientes", jPanel1);

        jPanel2.setLayout(null);

        jPanel7.setPreferredSize(new java.awt.Dimension(800, 320));
        jPanel7.setLayout(null);

        tabelaPedidos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "CLIENTE", "STATUS", "QTD. ITENS", "VALOR TOTAL"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaPedidos.setToolTipText("");
        jScrollPane3.setViewportView(tabelaPedidos);

        jPanel7.add(jScrollPane3);
        jScrollPane3.setBounds(10, 80, 440, 290);

        ListarItens.setText("LISTAR ITENS");
        ListarItens.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ListarItensActionPerformed(evt);
            }
        });
        jPanel7.add(ListarItens);
        ListarItens.setBounds(460, 80, 320, 23);

        Status3.setText("MUDAR STATUS PARA ENTREGUE");
        Status3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Status3ActionPerformed(evt);
            }
        });
        jPanel7.add(Status3);
        Status3.setBounds(460, 170, 320, 23);

        lbErroPedidos.setForeground(new java.awt.Color(255, 51, 0));
        lbErroPedidos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbErroPedidos.setText("SELECIONE AO MENOS UM PEDIDO");
        jPanel7.add(lbErroPedidos);
        lbErroPedidos.setBounds(10, 390, 450, 14);

        buscar_pedidos.setText("BUSCAR");
        buscar_pedidos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscar_pedidosActionPerformed(evt);
            }
        });
        jPanel7.add(buscar_pedidos);
        buscar_pedidos.setBounds(460, 40, 320, 23);
        jPanel7.add(buscaPedidos);
        buscaPedidos.setBounds(10, 40, 440, 20);

        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Digite o que deseja buscar:");
        jPanel7.add(jLabel10);
        jLabel10.setBounds(10, 10, 230, 14);

        Status2.setText("MUDAR STATUS PARA A CAMINHO");
        Status2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Status2ActionPerformed(evt);
            }
        });
        jPanel7.add(Status2);
        Status2.setBounds(460, 140, 320, 23);

        Status1.setText("MUDAR STATUS PARA ABERTO");
        Status1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Status1ActionPerformed(evt);
            }
        });
        jPanel7.add(Status1);
        Status1.setBounds(460, 110, 320, 23);

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/pizzaback.jpg"))); // NOI18N
        jPanel7.add(jLabel11);
        jLabel11.setBounds(-10, -70, 820, 510);

        jPanel2.add(jPanel7);
        jPanel7.setBounds(0, 6, 806, 427);

        jTabbedPane1.addTab("Pedidos", jPanel2);

        jPanel4.setLayout(null);

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Digite o que deseja buscar:");
        jPanel4.add(jLabel2);
        jLabel2.setBounds(20, 10, 303, 14);
        jPanel4.add(campo_busca_sabores);
        campo_busca_sabores.setBounds(20, 30, 550, 23);

        tabelaSabores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "SABOR", "TIPO"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tabelaSabores);

        jPanel4.add(jScrollPane2);
        jScrollPane2.setBounds(20, 70, 546, 226);

        btBuscarSabores.setText("Buscar");
        btBuscarSabores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btBuscarSaboresActionPerformed(evt);
            }
        });
        jPanel4.add(btBuscarSabores);
        btBuscarSabores.setBounds(590, 30, 101, 23);

        btExcluirCliente1.setText("Excluir");
        btExcluirCliente1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btExcluirCliente1ActionPerformed(evt);
            }
        });
        jPanel4.add(btExcluirCliente1);
        btExcluirCliente1.setBounds(590, 70, 101, 23);

        lbErroSabor.setForeground(new java.awt.Color(255, 51, 51));
        lbErroSabor.setText("POR FAVOR, SELECIONE UMA LINHA.");
        jPanel4.add(lbErroSabor);
        lbErroSabor.setBounds(190, 300, 182, 26);

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Sabor:");
        jPanel4.add(jLabel3);
        jLabel3.setBounds(20, 330, 90, 14);

        tfInserir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfInserirActionPerformed(evt);
            }
        });
        jPanel4.add(tfInserir);
        tfInserir.setBounds(20, 350, 546, 20);

        btSalvarSabor.setText("SALVAR NOVO SABOR");
        btSalvarSabor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSalvarSaborActionPerformed(evt);
            }
        });
        jPanel4.add(btSalvarSabor);
        btSalvarSabor.setBounds(180, 400, 276, 23);

        lbInvalido.setForeground(new java.awt.Color(255, 51, 51));
        lbInvalido.setText("VALOR INVÁLIDO");
        jPanel4.add(lbInvalido);
        lbInvalido.setBounds(230, 370, 84, 14);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Tipo:");
        jPanel4.add(jLabel4);
        jLabel4.setBounds(600, 310, 64, 20);

        buttonGroup1.add(rbSimples);
        rbSimples.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rbSimples.setText("Simples");
        jPanel4.add(rbSimples);
        rbSimples.setBounds(600, 330, 80, 25);

        buttonGroup1.add(rbEspecial);
        rbEspecial.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rbEspecial.setText("Especial");
        jPanel4.add(rbEspecial);
        rbEspecial.setBounds(600, 350, 80, 25);

        buttonGroup1.add(rbPremium);
        rbPremium.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rbPremium.setText("Premium");
        jPanel4.add(rbPremium);
        rbPremium.setBounds(600, 370, 80, 25);

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/pizzaback.jpg"))); // NOI18N
        jPanel4.add(jLabel14);
        jLabel14.setBounds(3, -4, 810, 450);

        jTabbedPane1.addTab("Sabores", jPanel4);

        jPanel6.setLayout(null);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("TABELA DE PREÇOS");
        jPanel6.add(jLabel5);
        jLabel5.setBounds(278, 36, 211, 29);
        jPanel6.add(tfSimples);
        tfSimples.setBounds(189, 106, 390, 20);
        jPanel6.add(tfEspecial);
        tfEspecial.setBounds(189, 158, 390, 20);
        jPanel6.add(tfPremium);
        tfPremium.setBounds(189, 211, 390, 20);

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("SIMPLES:");
        jPanel6.add(jLabel6);
        jLabel6.setBounds(189, 84, 120, 14);

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("ESPECIAL:");
        jPanel6.add(jLabel7);
        jLabel7.setBounds(189, 140, 160, 14);

        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("PREMIUM:");
        jPanel6.add(jLabel8);
        jLabel8.setBounds(189, 192, 140, 14);

        SalvarValores.setText("SALVAR VALORES");
        SalvarValores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalvarValoresActionPerformed(evt);
            }
        });
        jPanel6.add(SalvarValores);
        SalvarValores.setBounds(262, 273, 246, 35);

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/pizzaback.jpg"))); // NOI18N
        jPanel6.add(jLabel12);
        jLabel12.setBounds(0, -30, 820, 480);

        jTabbedPane1.addTab("Valores", jPanel6);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 806, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 472, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btBuscarActionPerformed
         imprimeTodos();
    }//GEN-LAST:event_btBuscarActionPerformed

    private void btEditarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEditarClienteActionPerformed
        try {
            Cliente c = new Cliente();

            c.setId(Integer.parseInt(tabela.getValueAt(tabela.getSelectedRow(), 0).toString()));
            c.setNome(tabela.getValueAt(tabela.getSelectedRow(), 1).toString());
            c.setSobrenome(tabela.getValueAt(tabela.getSelectedRow(), 2).toString());
            c.setTelefone(tabela.getValueAt(tabela.getSelectedRow(), 3).toString());
            c.setEndereco(tabela.getValueAt(tabela.getSelectedRow(), 4).toString());

            new ClienteForm(this, c).setVisible(true);
            lbErro.setVisible(false);
        } catch (Exception ex) {
            lbErro.setVisible(true);
        }
    }//GEN-LAST:event_btEditarClienteActionPerformed

    private void btAdicionarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAdicionarClienteActionPerformed
        ClienteForm cf = new ClienteForm(this);
        cf.setVisible(true);
    }//GEN-LAST:event_btAdicionarClienteActionPerformed

    private void btBuscarSaboresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btBuscarSaboresActionPerformed
        imprimeTodosSabores();
    }//GEN-LAST:event_btBuscarSaboresActionPerformed

    private void tfInserirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfInserirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfInserirActionPerformed

    private void btSalvarSaborActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSalvarSaborActionPerformed
        if (!tfInserir.getText().equals("")) {
            lbErroSabor.setVisible(false);
            Connection con;
            try {
                con = ConnectionFactory.getConnection("jdbc:mysql://localhost:3307/db_pizza", "root", "", ConnectionFactory.MYSQL);
                SaborDao sDao = new SaborDaoImpl(con);

                Sabor s = new Sabor();

                int tipo = 1;
                if (rbEspecial.isSelected()) {
                    tipo = 2;
                } else if (rbPremium.isSelected()) {
                    tipo = 3;
                }

                s.setNome(tfInserir.getText());
                s.setTipo(tipo);

                sDao.inserirSabor(s);

                con.close();
                imprimeTodosSabores();

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ClienteForm.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(ClienteForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            lbInvalido.setVisible(true);
        }
    }//GEN-LAST:event_btSalvarSaborActionPerformed

    private void btExcluirClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btExcluirClienteActionPerformed
        try {
            Connection con;
            try {
                con = ConnectionFactory.getConnection("jdbc:mysql://localhost:3307/db_pizza", "root", "", ConnectionFactory.MYSQL);
                new ClienteDaoImpl(con).removerCliente(Integer.parseInt(tabela.getValueAt(tabela.getSelectedRow(), 0).toString()));
                new PedidoDaoImpl(con).removerPedidosByIdCliente(Integer.parseInt(tabela.getValueAt(tabela.getSelectedRow(), 0).toString()));
                con.close();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ClienteForm.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(ClienteForm.class.getName()).log(Level.SEVERE, null, ex);
            }
            lbErro.setVisible(false);
            imprimeTodos();
            imprimeTodosPedidos();
        } catch (Exception ex) {
            lbErro.setVisible(true);
        }
    }//GEN-LAST:event_btExcluirClienteActionPerformed

    private void btExcluirCliente1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btExcluirCliente1ActionPerformed
        try {
            Connection con;
            try {
                con = ConnectionFactory.getConnection("jdbc:mysql://localhost:3307/db_pizza", "root", "", ConnectionFactory.MYSQL);
                new SaborDaoImpl(con).removerSabor(Integer.parseInt(tabelaSabores.getValueAt(tabelaSabores.getSelectedRow(), 0).toString()));
                con.close();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ClienteForm.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(ClienteForm.class.getName()).log(Level.SEVERE, null, ex);
            }
            lbErroSabor.setVisible(false);
            imprimeTodosSabores();
        } catch (Exception ex) {
            lbErroSabor.setVisible(true);
        }
    }//GEN-LAST:event_btExcluirCliente1ActionPerformed

    private void SalvarValoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalvarValoresActionPerformed
        if (!tfSimples.getText().equals(Float.toString(this.s)) || !tfEspecial.getText().equals(Float.toString(this.e)) || !tfPremium.getText().equals(Float.toString(this.p))) {
            try {
                this.s = Float.parseFloat(tfSimples.getText());
                try {
                    this.e = Float.parseFloat(tfEspecial.getText());
                    try {
                        this.p = Float.parseFloat(tfPremium.getText());
                        insereValor();
                    } catch (NumberFormatException e) {
                        System.out.println("MOSTRAR ERRO PREMIUM");
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("MOSTRAR ERRO ESPECIAL");
                }
            } catch (NumberFormatException e) {
                System.out.println("MOSTRAR ERRO SIMPLES");
            }
        }
    }//GEN-LAST:event_SalvarValoresActionPerformed

    private void AddPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddPedidoActionPerformed
        try {
            Cliente c = new Cliente();
            PizzaForm pf = new PizzaForm(this, Integer.parseInt(tabela.getValueAt(tabela.getSelectedRow(), 0).toString()));
            pf.setVisible(true);
            lbErro.setVisible(false);
        } catch (Exception ex) {
            lbErro.setVisible(true);
        }
    }//GEN-LAST:event_AddPedidoActionPerformed

    private void buscar_pedidosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscar_pedidosActionPerformed
        imprimeTodosPedidos();
    }//GEN-LAST:event_buscar_pedidosActionPerformed

    private void campo_buscaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campo_buscaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campo_buscaActionPerformed

    private void Status3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Status3ActionPerformed
        try {
            Connection con;
            try {
                con = ConnectionFactory.getConnection("jdbc:mysql://localhost:3307/db_pizza", "root", "", ConnectionFactory.MYSQL);
                new PedidoDaoImpl(con).UpdateStatus(Integer.parseInt(tabelaPedidos.getValueAt(tabelaPedidos.getSelectedRow(), 0).toString()), 3);
                con.close();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ClienteForm.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(ClienteForm.class.getName()).log(Level.SEVERE, null, ex);
            }
            lbErroPedidos.setVisible(false);
            imprimeTodosPedidos();
        } catch (Exception ex) {
            lbErroPedidos.setVisible(true);
        }
    }//GEN-LAST:event_Status3ActionPerformed

    private void Status2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Status2ActionPerformed
        try {
            Connection con;
            try {
                con = ConnectionFactory.getConnection("jdbc:mysql://localhost:3307/db_pizza", "root", "", ConnectionFactory.MYSQL);
                new PedidoDaoImpl(con).UpdateStatus(Integer.parseInt(tabelaPedidos.getValueAt(tabelaPedidos.getSelectedRow(), 0).toString()), 2);
                con.close();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ClienteForm.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(ClienteForm.class.getName()).log(Level.SEVERE, null, ex);
            }
            lbErroPedidos.setVisible(false);
            imprimeTodosPedidos();
        } catch (Exception ex) {
            lbErroPedidos.setVisible(true);
        }
    }//GEN-LAST:event_Status2ActionPerformed

    private void Status1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Status1ActionPerformed
        try {
            Connection con;
            try {
                con = ConnectionFactory.getConnection("jdbc:mysql://localhost:3307/db_pizza", "root", "", ConnectionFactory.MYSQL);
                new PedidoDaoImpl(con).UpdateStatus(Integer.parseInt(tabelaPedidos.getValueAt(tabelaPedidos.getSelectedRow(), 0).toString()), 1);
                con.close();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ClienteForm.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(ClienteForm.class.getName()).log(Level.SEVERE, null, ex);
            }
            lbErroPedidos.setVisible(false);
            imprimeTodosPedidos();
        } catch (Exception ex) {
            lbErroPedidos.setVisible(true);
        }
    }//GEN-LAST:event_Status1ActionPerformed

    private void ListarItensActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ListarItensActionPerformed
        try {
            Connection con;
            con = ConnectionFactory.getConnection("jdbc:mysql://localhost:3307/db_pizza", "root", "", ConnectionFactory.MYSQL);
            PedidoDaoImpl pDaoImp = new PedidoDaoImpl(con);
            int idCliente = pDaoImp.GetIdClienteByIdPedido(Integer.parseInt(tabelaPedidos.getValueAt(tabelaPedidos.getSelectedRow(), 0).toString()));
            PizzaForm pf = new PizzaForm(this, idCliente);
            pf.setVisible(true);
            lbErroPedidos.setVisible(false);
        } catch (Exception ex) {
            lbErroPedidos.setVisible(true);
        }
    }//GEN-LAST:event_ListarItensActionPerformed

    public void insereValor() throws ClassNotFoundException {
        Connection con;
        try {
            con = ConnectionFactory.getConnection("jdbc:mysql://localhost:3307/db_pizza", "root", "", ConnectionFactory.MYSQL);

            TipoDao t = new TipoDaoImpl(con);

            t.setPrecoSimples(this.s);
            t.setPrecoEspecial(this.e);
            t.setPrecoPremium(this.p);

            tfSimples.setText(Float.toString(this.s));
            tfEspecial.setText(Float.toString(this.e));
            tfPremium.setText(Float.toString(this.p));

            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Main main = null;
                try {
                    main = new Main();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
                main.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddPedido;
    private javax.swing.JButton ListarItens;
    private javax.swing.JButton SalvarValores;
    private javax.swing.JButton Status1;
    private javax.swing.JButton Status2;
    private javax.swing.JButton Status3;
    private javax.swing.JButton btAdicionarCliente;
    private javax.swing.JButton btBuscar;
    private javax.swing.JButton btBuscarSabores;
    private javax.swing.JButton btEditarCliente;
    private javax.swing.JButton btExcluirCliente;
    private javax.swing.JButton btExcluirCliente1;
    private javax.swing.JButton btSalvarSabor;
    private javax.swing.JTextField buscaPedidos;
    private javax.swing.JButton buscar_pedidos;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JTextField campo_busca;
    private javax.swing.JTextField campo_busca_sabores;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lbErro;
    private javax.swing.JLabel lbErroPedidos;
    private javax.swing.JLabel lbErroSabor;
    private javax.swing.JLabel lbInvalido;
    private javax.swing.JRadioButton rbEspecial;
    private javax.swing.JRadioButton rbPremium;
    private javax.swing.JRadioButton rbSimples;
    private javax.swing.JTable tabela;
    private javax.swing.JTable tabelaPedidos;
    private javax.swing.JTable tabelaSabores;
    private javax.swing.JTextField tfEspecial;
    private javax.swing.JTextField tfInserir;
    private javax.swing.JTextField tfPremium;
    private javax.swing.JTextField tfSimples;
    // End of variables declaration//GEN-END:variables

//    public void setModel(DefaultTableModel model) {
//        this.model = model;
//    }
}
