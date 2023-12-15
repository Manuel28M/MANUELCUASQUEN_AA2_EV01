
package SistemaInventario;

import sistemainventario.gui.VentanaProductos;

public class SistemaInventario {
    public static void SistemaInventario(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaProductos().setVisible(true);
            }
        });
    }
}

