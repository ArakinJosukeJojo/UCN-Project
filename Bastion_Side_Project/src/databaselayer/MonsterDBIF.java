package databaselayer;

import java.util.List;

import model.Monster;

public interface MonsterDBIF {

	public Monster addMonster(Monster monster) throws DataAccessException;

	public List<Monster> findMonsterByName(String monster) throws DataAccessException;

}
