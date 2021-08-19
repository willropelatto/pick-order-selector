package br.com.pofexo.pickselector.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

public class Selector {

	public static void main(String[] args) {
		Selector t = new Selector();
		t.main();
	}

	void main() {
		Set<Integer> teams = Set.of(1, 2, 3);
		Integer rounds = 5;
		Integer qtd = teams.size() * rounds;
		Set<Integer> usedTeams;
		List<Integer> draft = new LinkedList<>();
		List<TeamPick> draft1 = new LinkedList<>();

		Map<Integer, List<TeamPick>> map = new HashMap<>();
		for (int i = 1; i <= rounds; i++) {
			List<TeamPick> list = new ArrayList<>();
			teams.forEach(team -> {
				TeamPick tp = new TeamPick(team);
				list.add(tp);
			});
			map.put(i, list);
		}

		for (int i = 1; i <= rounds; i++) {
			usedTeams = new HashSet<>();
			for (int j = 1; j <= teams.size(); j++) {
				List<TeamPick> list = map.get(j);
				TeamPick next = next(list, usedTeams);
				qtd = addPick(next, qtd, usedTeams, draft);
				draft1.add(next);
			}
		}

		System.out.println(draft);
		System.out.println(draft1);
	}

	TeamPick next(List<TeamPick> lista, Set<Integer> usedTeams) {
		List<TeamPick> collect = lista.stream().filter(x -> !usedTeams.contains(x.getTeam()))
				.collect(Collectors.toList());

		if (CollectionUtils.isEmpty(collect))
			return null;

		collect.sort(Comparator.comparing(TeamPick::getQuantity).thenComparing(TeamPick::getPriority));
		TeamPick lastElement = collect.get(0);

		return lastElement;
	}

	public Integer addPick(TeamPick tp, Integer qtd, Set<Integer> usedTeams, List<Integer> draft) {
		tp.setPriority(tp.getPriority() + qtd);
		tp.setQuantity(tp.getQuantity() + 1);
		usedTeams.add(tp.getTeam());
		draft.add(tp.getTeam());
		return qtd - 1;
	}

}

class TeamPick {

	@Override
	public String toString() {
		return "TeamPick [team=" + team + ", quantity=" + quantity + ", priority=" + priority + "]";
	}

	TeamPick(Integer team) {
		this.team = team;
	}

	Integer team;
	Integer quantity = 0;
	Integer priority = 0;

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Integer getTeam() {
		return team;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public Integer getPriority() {
		return priority;
	}

}