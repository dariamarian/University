package repository.DataBase;

import domain.Message;
import exceptions.RepoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MessageDBRepo extends AbstractDBRepo<Message> {
    private UtilizatorDBRepo userRepo;
    public MessageDBRepo(String url, String userName, String password, UtilizatorDBRepo userRepo) {
        super(url, userName, password);
        this.userRepo=userRepo;
        loadData();
    }

    @Override
    protected List <Message> extractEntity(ResultSet resultSet) throws SQLException {
        List<Message> messageList=new ArrayList<>();
        while (resultSet.next()) {
            Long idMessage = resultSet.getLong("id_message");
            Long idSender = resultSet.getLong("id_sender");
            Long idReceiver = resultSet.getLong("id_receiver");
            String time = resultSet.getString("timeSent");
            String messageText = resultSet.getString("messageText");

            try {
                Message message = new Message(idSender, idReceiver, messageText);
                message.setId(idMessage);
                message.setTimeSent(time);
                messageList.add(message);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return messageList;
    }

    @Override
    protected PreparedStatement ps_getAll(Connection connection) throws SQLException {
        return connection.prepareStatement("SELECT * FROM messages");
    }

    @Override
    protected void storeEntity(Message entity, Connection connection) throws SQLException, RepoException {
        String sql="INSERT INTO messages(id_message,id_sender,id_receiver,timesent,messagetext) values (?,?,?,?,?)";
        PreparedStatement ps=connection.prepareStatement(sql);
        ps.setLong(1,entity.getId());
        ps.setLong(2,entity.getIdSender());
        ps.setLong(3,entity.getIdReceiver());
        ps.setString(4, entity.getTimeSentString());
        ps.setString(5,entity.getMessageText());
        ps.executeUpdate();
    }

    @Override
    protected void deleteEntity(Message entity, Connection connection) throws SQLException {
        String sql = "DELETE FROM messages WHERE id_message=?";
        PreparedStatement ps=connection.prepareStatement(sql);
        ps.setLong(1,entity.getId());
        ps.executeUpdate();
    }
}