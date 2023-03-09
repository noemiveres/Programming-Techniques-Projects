package dataAcess.dao;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import dataAcess.connection.ConnectionFactory;

/**
 * A generic class containing the methods needed to operate the databases.
 * @param <T> specifies the type of the entity which is selected, inserted, updated, or deleted
 *           from the database.
 * @Author: Veres Noemi
 * @Since: Apr 14, 2022
 */
public class AbstractDAO<T> {
	protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

	private final Class<T> type;

	@SuppressWarnings("unchecked")
	public AbstractDAO() {
		this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	private String createSelectQuery(String field) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append(" * ");
		sb.append(" FROM `");
		sb.append(type.getSimpleName());
		sb.append("` WHERE " + field + " =?");
		return sb.toString();
	}

	/**
	 * Finds all instances in a database.
	 * @return a list containing all instances of T from the database.
	 */
	public List<T> findAll() {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = "SELECT * FROM `" + type.getSimpleName() + "`";
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			return createObjects(resultSet);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll " + e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
		return null;
	}
	/**
	 * Finds an instance from the database which has the given id.
	 * @param id is the searched primary key.
	 * @return the instance of T with the primary key equal to id.
	 */
	public T findById(int id) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = createSelectQuery("id");
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();

			return createObjects(resultSet).get(0);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
		return null;
	}

	private List<T> createObjects(ResultSet resultSet) {
		List<T> list = new ArrayList<T>();
		Constructor[] ctors = type.getDeclaredConstructors();
		Constructor ctor = null;
		for (int i = 0; i < ctors.length; i++) {
			ctor = ctors[i];
			if (ctor.getGenericParameterTypes().length == 0)
				break;
		}
		try {
			while (resultSet.next()) {
				ctor.setAccessible(true);
				T instance = (T)ctor.newInstance();
				for (Field field : type.getDeclaredFields()) {
					String fieldName = field.getName();
					Object value = resultSet.getObject(fieldName);
					PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
					Method method = propertyDescriptor.getWriteMethod();
					method.invoke(instance, value);
				}
				list.add(instance);
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		return list;
	}

	private String createColumnsListString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		int i = 1;
		for (Field field : type.getDeclaredFields()) {
			if(field.getName().equals("id")){
				continue;
			}
			sb.append(field.getName());
			if (i != type.getDeclaredFields().length - 1) {
				sb.append(", ");
			}
			i++;
		}
		sb.append(")");
		return sb.toString();
	}

	private String createEmptyColumnValuesString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		for (int i = 1; i < type.getDeclaredFields().length; i++) {
			sb.append("?");
			if (i != type.getDeclaredFields().length - 1) {
				sb.append(", ");
			}
		}
		sb.append(")");
		return sb.toString();
	}

	public String[] getColumnsList() {
		int N = type.getDeclaredFields().length;
		String columnMatrix[] = new String[N];
		int i = 0;
		for (Field field : type.getDeclaredFields()) {
			columnMatrix[i] = field.getName();
			i++;
		}
		return columnMatrix;
	}

	private String createEditedColumnsListString() {
		StringBuilder sb = new StringBuilder();
		int i = 0;
		for (Field field : type.getDeclaredFields()) {
			sb.append(field.getName()).append(" = ?");
			if (i != type.getDeclaredFields().length - 1) {
				sb.append(",");
			}
			sb.append(" ");
			i++;
		}
		return sb.toString();
	}

	/**
	 * Inserts into the database a new tuple corresponding to the entity.
	 * @param t is the entity to be inserted.
	 */
	public void insert(T t) {
		Connection connection = null;
		PreparedStatement statement = null;
		String query = "INSERT INTO `" + type.getSimpleName() + "` " + createColumnsListString() +
				" VALUES " + createEmptyColumnValuesString();
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);
			int index = 1;
			for (Field field : type.getDeclaredFields()) {
				if(field.getName().equals("id")){
					continue;
				}
				field.setAccessible(true);
				statement.setObject(index, field.get(t));
				field.setAccessible(false);
				index++;
			}

			statement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} finally {
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
	}

	/**
	 * Removes from the database all instances of T with the primary key qual to key.
	 * @param t is the entity to be updated.
	 */
	public void update(T t) {
		Connection connection = null;
		PreparedStatement statement = null;
		String query = "UPDATE `" + type.getSimpleName() + "` SET " + createEditedColumnsListString() +
				" WHERE id = ?";
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);
			int index = 1;
			for (Field field : type.getDeclaredFields()) {
				field.setAccessible(true);
				statement.setObject(index, field.get(t));
				field.setAccessible(false);
				index++;
			}
			Field primaryKey = null;
			for(Field field : type.getDeclaredFields()){
				if(field.getName().equals("id")){
					primaryKey = field;
				}
			}
			primaryKey.setAccessible(true);
			statement.setObject(index, primaryKey.get(t));
			primaryKey.setAccessible(false);
			statement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:update " + e.getMessage());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} finally {
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
	}
	/**
	 * Deletes from the database all instances of T.
	 * @param t is the entity to be deleted.
	 */
	public void delete(T t){
		Connection connection = null;
		PreparedStatement statement = null;
		String query = "DELETE FROM `" + type.getSimpleName() + "` WHERE id = ?";
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);
			Field primaryKey = null;
			for(Field field : type.getDeclaredFields()){
				if(field.getName().equals("id")){
					primaryKey = field;
				}
			}
			primaryKey.setAccessible(true);
			statement.setObject(1,primaryKey.get(t));
			primaryKey.setAccessible(false);
			statement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:delete " + e.getMessage());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} finally {
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
	}
}
