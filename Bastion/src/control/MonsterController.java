package control;

import java.util.List;

import databaselayer.DataAccessException;
import databaselayer.MonsterDB;
import databaselayer.MonsterDBIF;
import model.Monster;

public class MonsterController {

	private MonsterDBIF monsterDB;

	public MonsterController() throws DataAccessException {
		monsterDB = new MonsterDB();
	}

	public Monster addMonster(Monster monster) throws DataAccessException {
		monsterDB.addMonster(monster);
		return monster;
	}

	public List<Monster> findMonsterByName(String name) throws DataAccessException {
		List<Monster> m = monsterDB.findMonsterByName(name);
		return m;
	}
}
