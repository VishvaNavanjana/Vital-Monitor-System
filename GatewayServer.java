import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.net.ServerSocket;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;


public class GatewayServer {

    //method to create a datagram socket to retrive broadcast packets
    public static DatagramSocket createSocket(int port) throws SocketException {
        DatagramSocket socket = new DatagramSocket(port);
        return socket;
    }

    public static void main(String[] args) throws ClassNotFoundException, IOException {
        // discover vital monitors that are broadcasting via port 6000
        int port = 6000;

        // Create a datagram socket to receive broadcast packets
        DatagramSocket socket = null;
        try {
            socket = createSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }

        // A list to store vital monitors
        ArrayList<String> vitalMonitors = new ArrayList<>();

        // keep running gateway server
        while (true) {
            // Create a buffer for incoming data
            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            // Block until a datagram is received
            try {
                socket.receive(packet);

                // Convert the buffer into a monitor object
                ByteArrayInputStream byteStream = new ByteArrayInputStream(packet.getData());
                ObjectInputStream objectStream = new ObjectInputStream(byteStream);
                Monitor monitor = (Monitor) objectStream.readObject();

                 // check whether the monitor is already in the list
                if (!vitalMonitors.contains(monitor.getMonitorID())) {
                    // add the monitor to the list
                    vitalMonitors.add(monitor.getMonitorID());
                    // print the monitor
                    System.out.println(monitor.monitor_str());
                    }


            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
