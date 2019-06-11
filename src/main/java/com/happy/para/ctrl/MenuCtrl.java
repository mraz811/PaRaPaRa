package com.happy.para.ctrl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.happy.para.dto.FileDto;
import com.happy.para.dto.MenuDto;
import com.happy.para.dto.OwnerDto;
import com.happy.para.model.FileUploadService;
import com.happy.para.model.Menu_IService;

@Controller
public class MenuCtrl {

	// 메뉴
	@Autowired
	private Menu_IService menu_IService;

	// 업주 : 판매 메뉴 처음 들어왔을때
	@RequestMapping(value = "/ownerMenuList.do", method = RequestMethod.GET)
	public String ownerMenuList(Model model, HttpSession session) {
		String menu_category = "주메뉴";
		OwnerDto dto = (OwnerDto) session.getAttribute("loginDto");
		System.out.println(dto);
		
		//1,2,3,4, 로 업주테이블에 저장된 업주선택메뉴를 문자열자르기를 통해 정제하는 로직
		if (session.getAttribute("owner_menu") == null) {
			System.out.println("loginDto 세션에 값 있을때");
			String owner_menu = dto.getOwner_menu();
			String[] menu_seq = new String[owner_menu.split(",").length];
			for (int i = 0; i < owner_menu.split(",").length; i++) {
				menu_seq[i] = owner_menu.split(",")[i];
				System.out.print(owner_menu.split(",")[i]);
			}
			System.out.println("길이" + owner_menu.split(",").length);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("menu_seq_", menu_seq);
			map.put("menu_category", menu_category);
			List<MenuDto> lists = menu_IService.ownerMenuList(map);
			System.out.println("업주가 선택한 메뉴 : " + lists.toString());
			model.addAttribute("menuList", lists);
		} else {
			System.out.println("owner_menu 세션에 값 있을때");
			String owner_menu = (String) session.getAttribute("owner_menu");
			String[] menu_seq = new String[owner_menu.split(",").length];
			for (int i = 0; i < owner_menu.split(",").length; i++) {
				menu_seq[i] = owner_menu.split(",")[i];
				System.out.print(owner_menu.split(",")[i]);
			}
			System.out.println("길이" + owner_menu.split(",").length);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("menu_seq_", menu_seq);
			map.put("menu_category", menu_category);
			List<MenuDto> lists = menu_IService.ownerMenuList(map);
			System.out.println("업주가 선택한 메뉴 : " + lists.toString());
			model.addAttribute("menuList", lists);
		}
		return "/menu/menuListSell";
	}

	// 업주 : 판매 메뉴 리스트 2depth, 카테고리 버튼 눌렀을때
	@RequestMapping(value = "/choiceMenuList.do", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject ownerMenuList(HttpSession session, String menu_category) {
		System.out.println("-----------------" + menu_category);
		OwnerDto dto = (OwnerDto) session.getAttribute("loginDto");
		JSONObject json = null;
		
		//1,2,3,4, 로 업주테이블에 저장된 업주선택메뉴를 문자열자르기를 통해 정제하는 로직
		if (session.getAttribute("owner_menu") == null) {
			String owner_menu = dto.getOwner_menu();
			String[] menu_seq = new String[owner_menu.split(",").length];
			for (int i = 0; i < owner_menu.split(",").length; i++) {
				menu_seq[i] = owner_menu.split(",")[i];
				System.out.print(owner_menu.split(",")[i]);
			}
			System.out.println("길이" + owner_menu.split(",").length);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("menu_seq_", menu_seq);
			map.put("menu_category", menu_category);
			List<MenuDto> lists = menu_IService.ownerMenuList(map);
			System.out.println("업주가 선택한 메뉴 : " + lists.toString());
			json = objectJson(lists);
		} else {
			String owner_menu = (String) session.getAttribute("owner_menu");
			String[] menu_seq = new String[owner_menu.split(",").length];
			for (int i = 0; i < owner_menu.split(",").length; i++) {
				menu_seq[i] = owner_menu.split(",")[i];
				System.out.print(owner_menu.split(",")[i]);
			}
			System.out.println("길이" + owner_menu.split(",").length);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("menu_seq_", menu_seq);
			map.put("menu_category", menu_category);
			List<MenuDto> lists = menu_IService.ownerMenuList(map);
			System.out.println("업주가 선택한 메뉴 : " + lists.toString());
			json = objectJson(lists);
		}
		return json;
	}

	@SuppressWarnings("unchecked")
	private JSONObject objectJson(List<MenuDto> lists) {
		JSONObject json = new JSONObject();
		JSONArray jLists = new JSONArray();
		JSONObject jList = null;
		for (int i = 0; i < lists.size(); i++) {
			jList = new JSONObject();
			jList.put("file_seq", lists.get(i).getFileDto().getFile_seq());
			jList.put("file_tname", lists.get(i).getFileDto().getFile_tname());
			jList.put("file_rname", lists.get(i).getFileDto().getFile_rname());
			jList.put("file_aurl", lists.get(i).getFileDto().getFile_aurl());
			jList.put("file_rurl", lists.get(i).getFileDto().getFile_rurl());
			jList.put("menu_seq", lists.get(i).getMenu_seq());
			jList.put("menu_name", lists.get(i).getMenu_name());
			jList.put("menu_price", lists.get(i).getMenu_price());
			jList.put("menu_category", lists.get(i).getMenu_category());
			jList.put("menu_delflag", lists.get(i).getMenu_delflag());

			jLists.add(jList);
		}
		json.put("choiceMenu", jLists);
		System.out.println(json + "가가가");
		System.out.println(json.toString() + "나나나");
		return json;
	}

	// 업주 : 메뉴 삭제 버튼
	@RequestMapping(value = "/menuCancel.do", method = RequestMethod.POST, produces = "application/text; charset=UTF-8")
	@ResponseBody
	public String ownerMenuCancel(HttpSession session, String[] cancel_menu_seq) {
		OwnerDto dto = (OwnerDto) session.getAttribute("loginDto");
		boolean isc = false;
		
		//1,2,3,4, 로 업주테이블에 저장된 업주선택메뉴를 문자열자르기를 통해 정제하는 로직
		//업주가 삭제하기 위해 선택한 메뉴와 업주가 선택한 메뉴가 같다면 삭제 아니면 삭제하지 않음
		if (session.getAttribute("owner_menu") == null) {
			String owner_menu = dto.getOwner_menu();
			String owner_seq = Integer.toString(dto.getOwner_seq());
			for (int i = 0; i < cancel_menu_seq.length; i++) {
				System.out.println("지울 메뉴 번호 : " + cancel_menu_seq[i]);
			}
			String[] menu_seq = new String[owner_menu.split(",").length];
			for (int i = 0; i < owner_menu.split(",").length; i++) {
				menu_seq[i] = owner_menu.split(",")[i];
			}
			Map<String, Object> map1 = new HashMap<String, Object>();
			map1.put("menu_seq_", menu_seq);
			List<MenuDto> lists = menu_IService.ownerMenuList(map1);

			System.out.println("조회해올 현재 업주 선택메뉴 리스트 : " + lists.toString());

			List<MenuDto> deleteLists = new ArrayList<>();
			for (int i = 0; i < cancel_menu_seq.length; i++) {
				deleteLists.add(menu_IService.detailMenu(cancel_menu_seq[i]));
			}

			System.out.println("삭제할 메뉴 : " + deleteLists.toString());
			System.out.println("업주 선택 메뉴 리스트 크기 : " + lists.size());
			System.out.println("삭제할 메뉴 번호 리스트 크기 : " + deleteLists.size());
			int ownerMenuSize = lists.size();
			int deleteMenuSize = deleteLists.size();
			int[] ownerMenu = new int[deleteMenuSize];

			for (int i = 0; i < ownerMenu.length; i++) {
				ownerMenu[i] = deleteLists.get(i).getMenu_seq();
			}
			for (int i = 0; i < ownerMenu.length; i++) {
				System.out.println("ownerMenu.length로 몇번도냐 : " + i);
				for (int j = 0; j < ownerMenuSize; j++) {
					System.out.println("ownerMenuSize로 몇번도냐 : " + j);
					System.out.println("메뉴seq : " + lists.get(j).getMenu_seq());
					if (ownerMenu[i] == lists.get(j).getMenu_seq()) {
						System.out.println("삭제 됐다!! : " + lists.get(j).getMenu_seq());
						lists.remove(lists.get(j));
						ownerMenuSize--;
					}
				}
			}
			String newOwner_menu = "";
			for (int i = 0; i < lists.size(); i++) {
				newOwner_menu += lists.get(i).getMenu_seq() + ",";
			}
			System.out.println("바뀔 업주 선택 메뉴 번호들 : " + newOwner_menu);
			System.out.println("-=-=-==-=-=-=-=-=-=-=-" + newOwner_menu);
			Map<String, String> map2 = new HashMap<String, String>();
			map2.put("owner_menu", newOwner_menu);
			map2.put("owner_seq", owner_seq); // 세션에서 받아올꺼
			isc = menu_IService.ownerMenuChoice(map2);
			System.out.println("업주의 메뉴 선택 성공? : " + isc);
			session.setAttribute("owner_menu", newOwner_menu);
		} else {
			String owner_menu = (String) session.getAttribute("owner_menu");
			String owner_seq = Integer.toString(dto.getOwner_seq());
			for (int i = 0; i < cancel_menu_seq.length; i++) {
				System.out.println("지울 메뉴 번호 : " + cancel_menu_seq[i]);
			}
			String[] menu_seq = new String[owner_menu.split(",").length];
			for (int i = 0; i < owner_menu.split(",").length; i++) {
				menu_seq[i] = owner_menu.split(",")[i];
			}
			Map<String, Object> map1 = new HashMap<String, Object>();
			map1.put("menu_seq_", menu_seq);
			List<MenuDto> lists = menu_IService.ownerMenuList(map1); // 현재 업주 선택 메뉴

			System.out.println("조회해올 현재 업주 선택메뉴 리스트 : " + lists);

			List<MenuDto> deleteLists = new ArrayList<>();
			for (int i = 0; i < cancel_menu_seq.length; i++) {
				deleteLists.add(menu_IService.detailMenu(cancel_menu_seq[i])); // 삭제할 메뉴
			}

			System.out.println("삭제할 메뉴 : " + deleteLists);
			System.out.println("업주 선택 메뉴 리스트 크기 : " + lists.size());
			System.out.println("삭제할 메뉴 번호 리스트 크기 : " + deleteLists.size());
			System.out.println("list : " + lists);
			System.out.println("deletelist : " + deleteLists);
			int ownerMenuSize = lists.size();
			int deleteMenuSize = deleteLists.size();
			int[] ownerMenu = new int[deleteMenuSize];

			for (int i = 0; i < ownerMenu.length; i++) {
				ownerMenu[i] = deleteLists.get(i).getMenu_seq();
			}
			for (int i = 0; i < ownerMenu.length; i++) {
				System.out.println("ownerMenu.length로 몇번도냐 : " + i);
				for (int j = 0; j < ownerMenuSize; j++) {
					System.out.println("ownerMenuSize로 몇번도냐 : " + j);
					System.out.println("메뉴seq : " + lists.get(j).getMenu_seq());
					if (ownerMenu[i] == lists.get(j).getMenu_seq()) {
						System.out.println("삭제 됐다!! : " + lists.get(j).getMenu_seq());
						lists.remove(lists.get(j));
						ownerMenuSize--;
					}
				}
			}
			String newOwner_menu = "";
			for (int i = 0; i < lists.size(); i++) {
				newOwner_menu += lists.get(i).getMenu_seq() + ",";
			}
			System.out.println("바뀔 업주 선택 메뉴 번호들 : " + newOwner_menu);
			System.out.println("-=-=-==-=-=-=-=-=-=-=-" + newOwner_menu);
			Map<String, String> map2 = new HashMap<String, String>();
			map2.put("owner_menu", newOwner_menu);
			map2.put("owner_seq", owner_seq); // 세션에서 받아올꺼
			isc = menu_IService.ownerMenuChoice(map2);
			System.out.println("업주의 메뉴 선택 성공? : " + isc);
			session.removeAttribute("owner_menu");
			session.setAttribute("owner_menu", newOwner_menu);
		}
 
		return isc?"성공":"실패";
	}

	// 업주 : 전체 메뉴 처음들어왔을때
	@RequestMapping(value = "/ownerAllMenuList.do", method = RequestMethod.GET)
	public String ownerMenuList(Model model) {
		String menu_category = "주메뉴";
		MenuDto dto = new MenuDto();
		dto.setMenu_category(menu_category);
		List<MenuDto> lists = menu_IService.allMenu(dto);
		System.out.println("전체 메뉴 조회 : " + lists);
		model.addAttribute("menuList", lists);
		return "/menu/menuListOwner";
	}

	// 업주 : 전체 메뉴 에서 카테고리 버튼 눌럿을때
	@RequestMapping(value = "/OselAllMenuList.do", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject OallMenu(String menu_category) {
		MenuDto dto = new MenuDto();
		dto.setMenu_category(menu_category);
		List<MenuDto> lists = menu_IService.allMenu(dto);
		System.out.println("전체 메뉴 조회 : " + lists);
		JSONObject json = null;
		json = objectJson(lists);
		return json;
	}

	// 업주 : 판매 메뉴 선택 버튼
	@RequestMapping(value = "/menuChoice.do", method = RequestMethod.POST)
	@ResponseBody
	public String ownerMenuChoice(HttpSession session, String menu_seqs) {

		boolean ajaxIsc = false;

		String newOwner_menu = "";
		String[] menu_seq = menu_seqs.split(",");
		System.out.println("화면에서 넘겨받은거 : " + menu_seqs + "=================================");
		System.out.println(menu_seq[0]);
		for (int i = 0; i < menu_seq.length; i++) {
			newOwner_menu += menu_seq[i] + ",";
		}

		OwnerDto oDto = (OwnerDto) session.getAttribute("loginDto");
		if (session.getAttribute("owner_menu") == null) {
			String owner_seq = Integer.toString(oDto.getOwner_seq());
			Map<String, String> map = new HashMap<String, String>();

			String[] oldMenu = oDto.getOwner_menu().split(",");
			System.out.println("owner_menu 세션에 값이 없을때");
			System.out.println("loginDto 세션에 담긴 메뉴 : " + oldMenu);
			List<String> oldMenuList = new ArrayList<String>();
			for (int i = 0; i < oldMenu.length; i++) {
				oldMenuList.add(oldMenu[i]);
			} // oldMenuList에 값 넣는거
			String[] newMenu = newOwner_menu.split(",");
			List<String> newMenuList = new ArrayList<String>();
			for (int i = 0; i < newMenu.length; i++) {
				newMenuList.add(newMenu[i]);
			} // newMenuList에 값 넣는거

			if (newMenuList.size() > oldMenuList.size()) {
				for (int j = 0; j < oldMenuList.size(); j++) {
					if (!newMenuList.contains(oldMenuList.get(j))) {
						newMenuList.add(oldMenuList.get(j));
					}
				}
				String owner_menu = "";
				for (int i = 0; i < newMenuList.size(); i++) {
					owner_menu += newMenuList.get(i) + ",";
				}

				System.out.println("업주가 추가할 메뉴 : " + newMenuList);
				System.out.println("원래 메뉴 + 추가메뉴 : " + newMenuList);
				System.out.println("스트링배열로 바꾼거 : " + owner_menu);

				map.put("owner_menu", owner_menu);
				map.put("owner_seq", owner_seq); // 세션에서 받아올꺼
				boolean isc = menu_IService.ownerMenuChoice(map);
				System.out.println("업주의 메뉴 선택 성공? : " + isc);
				session.setAttribute("owner_menu", owner_menu);
				ajaxIsc = true;
			} // newMenuList.size()가 클때
			else {
				System.out.println("원래 업주가 선택한 메뉴 : " + oldMenuList);
				for (int j = 0; j < newMenuList.size(); j++) {
					if (!oldMenuList.contains(newMenuList.get(j))) {
						oldMenuList.add(newMenuList.get(j));
					}
				}
				String owner_menu = "";
				for (int i = 0; i < oldMenuList.size(); i++) {
					owner_menu += oldMenuList.get(i) + ",";
				}

				System.out.println("업주가 추가할 메뉴 : " + newMenuList);
				System.out.println("원래 메뉴 + 추가메뉴 : " + oldMenuList);
				System.out.println("스트링배열로 바꾼거 : " + owner_menu);

				map.put("owner_menu", owner_menu);
				map.put("owner_seq", owner_seq); // 세션에서 받아올꺼
				boolean isc = menu_IService.ownerMenuChoice(map);
				System.out.println("업주의 메뉴 선택 성공? : " + isc);
				session.setAttribute("owner_menu", owner_menu);
				ajaxIsc = true;
			} // oldMenuList.size()가 클때

		} else { // owner_menu 세션 값 있을때
			String owner_seq = Integer.toString(oDto.getOwner_seq());
			Map<String, String> map = new HashMap<String, String>();

			String oldMenu1 = (String) session.getAttribute("owner_menu");
			String[] oldMenu = oldMenu1.split(",");
			System.out.println("owner_menu 세션에 값이 있을때");
			System.out.println("owner_menu 세션에 담긴 메뉴 : " + oldMenu);
			List<String> oldMenuList = new ArrayList<String>();
			for (int i = 0; i < oldMenu.length; i++) {
				oldMenuList.add(oldMenu[i]);
			} // oldMenuList에 값 넣는거
			String[] newMenu = newOwner_menu.split(",");
			List<String> newMenuList = new ArrayList<String>();
			for (int i = 0; i < newMenu.length; i++) {
				newMenuList.add(newMenu[i]);
			} // newMenuList에 값 넣는거

			if (newMenuList.size() > oldMenuList.size()) {
				System.out.println("원래 메뉴 : " + oldMenuList);
				System.out.println("업주가 추가할 메뉴 : " + newMenuList);
				for (int j = 0; j < oldMenuList.size(); j++) {
					if (!newMenuList.contains(oldMenuList.get(j))) {
						newMenuList.add(oldMenuList.get(j));
					}
				}
				String owner_menu = "";
				for (int i = 0; i < newMenuList.size(); i++) {
					owner_menu += newMenuList.get(i) + ",";
				}

				System.out.println("원래 메뉴 + 추가메뉴 : " + newMenuList);
				System.out.println("스트링배열로 바꾼거 : " + owner_menu);

				map.put("owner_menu", owner_menu);
				map.put("owner_seq", owner_seq); // 세션에서 받아올꺼
				boolean isc = menu_IService.ownerMenuChoice(map);
				System.out.println("업주의 메뉴 선택 성공? : " + isc);
				session.removeAttribute("owner_menu");
				session.setAttribute("owner_menu", owner_menu);
				ajaxIsc = true;
			} // newMenuList.size()가 클때
			else {
				System.out.println("원래 업주가 선택한 메뉴 : " + oldMenuList);
				for (int j = 0; j < newMenuList.size(); j++) {
					if (!oldMenuList.contains(newMenuList.get(j))) {
						oldMenuList.add(newMenuList.get(j));
					}
				}
				String owner_menu = "";
				for (int i = 0; i < oldMenuList.size(); i++) {
					owner_menu += oldMenuList.get(i) + ",";
				}

				System.out.println("업주가 추가할 메뉴 : " + newMenuList);
				System.out.println("원래 메뉴 + 추가메뉴 : " + oldMenuList);
				System.out.println("스트링배열로 바꾼거 : " + owner_menu);

				map.put("owner_menu", owner_menu);
				map.put("owner_seq", owner_seq); // 세션에서 받아올꺼
				boolean isc = menu_IService.ownerMenuChoice(map);
				System.out.println("업주의 메뉴 선택 성공? : " + isc);
				session.removeAttribute("owner_menu");
				session.setAttribute("owner_menu", owner_menu);
				ajaxIsc = true;
			} // oldMenuList.size()가 클때
		}
		return ajaxIsc ? "success" : "fail";
	}

	// 담당자 : 1depth로 처음 메뉴 들어왓을때
	@RequestMapping(value = "/adminAllMenuList.do", method = RequestMethod.GET)
	public String adminMenuList(Model model) {
		String menu_category = "주메뉴";
		MenuDto dto = new MenuDto();
		dto.setMenu_category(menu_category);
		List<MenuDto> lists = menu_IService.allMenu(dto);
		System.out.println("전체 메뉴 조회 : " + lists);
		model.addAttribute("menuList", lists);
		return "/menu/menuListAdmin";
	}

	// 담당자 : 카테고리 버튼 눌럿을때
	@RequestMapping(value = "/AselAllMenuList.do", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject AallMenu(String menu_category) {
		MenuDto dto = new MenuDto();
		dto.setMenu_category(menu_category);
		List<MenuDto> lists = menu_IService.allMenu(dto);
		System.out.println("전체 메뉴 조회 : " + lists);
		JSONObject json = null;
		json = objectJson(lists);
		return json;
	}

	@Resource(name="menuUploadPath")
	private String menuUploadPath;
	
	@Autowired
	private FileUploadService fileUploadService;
	
	// 담당자 : 메뉴 등록 폼 이동
	@RequestMapping(value = "/menuRegiForm.do", method = RequestMethod.GET)
	public String menuList() {
		return "/menu/menuRegiForm";
	}

	// 담당자 : 메뉴 등록
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/regiNewMenu.do", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject insertMenu(MenuDto mDto,FileDto fDto) {
		boolean isc = menu_IService.insertMenu(mDto,fDto);
		System.out.println("담당자 메뉴 등록 성공? : " + isc);
		
		JSONObject json = new JSONObject();
		json.put("menu_name", mDto.getMenu_name());
		
		return json;
	}
	// 담당자 : 파일 임시 등록
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/insertMenuTempFile.do",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject insertMenuTempFile(MultipartFile file1, HttpServletRequest request) {
		FileDto fDto = new FileDto();
		String relativePath = request.getSession().getServletContext().getRealPath("/");
		System.out.println("파일 상대 경로 : "+relativePath);
		
		Map<String,String> map = fileUploadService.restore(file1,relativePath);
		fDto.setFile_aurl(map.get("file_aurl")); //절대 경로
		fDto.setFile_rurl(map.get("file_rurl")); //상대 경로
		fDto.setFile_tname(map.get("file_tname"));
		fDto.setFile_rname(map.get("file_rname"));
		fDto.setFile_size(Integer.parseInt(map.get("file_size")));
		
		FileDto fDtoSel = new FileDto();
		fDtoSel = menu_IService.insertMenuTempFile(fDto);
		System.out.println("담당자 파일 임시 등록 상세 조회? : " + fDtoSel);
		
		String rurl = fDtoSel.getFile_rurl();
		System.out.println("업주 파일의 상대경로 자르기 전 : "+rurl);
		fDtoSel.setFile_rurl(rurl.substring(rurl.indexOf("\\PaRaPaRa"), rurl.length()));
		System.out.println("업주 파일의 상대경로 자르기 후 : "+fDtoSel.getFile_rurl());
		
		JSONObject json = new JSONObject();
		json.put("fDtoSel", fDtoSel);
		
		return json;
	}
	
	// 담당자 : 메뉴 수정 폼 이동
	@RequestMapping(value = "/modifyMenuForm.do", method = RequestMethod.GET)
	public String detailMenu(Model model, String menu_seq) {
		MenuDto dto = menu_IService.detailMenu(menu_seq);
		model.addAttribute("menuDto", dto);
		return "/menu/menuModiForm";
	}

	// 담당자 : 메뉴 수정
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/menuModi.do", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject modifyMenu(MenuDto mDto,FileDto fDto) {
		JSONObject json = new JSONObject();
		boolean isc = menu_IService.modifyMenu(mDto,fDto);
		System.out.println("담당자 메뉴 수정 성공? : " + isc);
		MenuDto dto = menu_IService.detailMenu(Integer.toString(mDto.getMenu_seq()));
		json.put("detailMenu", dto);
		return json;
	}

	// 담당자 : 메뉴 수정 이미지(파일) 임시 등록
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/modifyMenuTempFile.do",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject modifyMenuTempFile(String file_seq,MultipartFile file1, HttpServletRequest request) {
		FileDto fDto = new FileDto();
		String relativePath = request.getSession().getServletContext().getRealPath("/");
		System.out.println("파일 상대 경로 : "+relativePath);
		
		fDto.setFile_seq(Integer.parseInt(file_seq));
		Map<String,String> map = fileUploadService.restore(file1,relativePath);
		fDto.setFile_aurl(map.get("file_aurl")); //절대 경로
		fDto.setFile_rurl(map.get("file_rurl").substring(map.get("file_rurl").indexOf("\\PaRaPaRa"), map.get("file_rurl").length()));//절대 경로
		fDto.setFile_tname(map.get("file_tname"));
		fDto.setFile_rname(map.get("file_rname"));
		fDto.setFile_size(Integer.parseInt(map.get("file_size")));
		
		FileDto fDtoSel = new FileDto();
		fDtoSel = menu_IService.modifyMenuTempFile(fDto);
		System.out.println("담당자 파일 임시 등록 상세 조회? : " + fDtoSel);
		
		JSONObject json = new JSONObject();
		json.put("fDtoSel", fDtoSel);
		
		return json;
	}
		
	// 담당자 : 메뉴 삭제
	@RequestMapping(value = "/delMenu.do", method = RequestMethod.GET)
	public String deleteMenu(String menu_seq, Model model) {
		System.out.println("넘겨받은 menu_seq : " + menu_seq);
		boolean isc = menu_IService.deleteMenu(menu_seq);
		System.out.println("담당자 메뉴 삭제 성공? : " + isc);
		String menu_category = "주메뉴";
		MenuDto dto = new MenuDto();
		dto.setMenu_category(menu_category);
		List<MenuDto> lists = menu_IService.allMenu(dto);
		for (int i = 0; i < lists.size(); i++) {
			String rurl = lists.get(i).getFileDto().getFile_rurl();
			System.out.println("업주 파일의 상대경로 자르기 전 : "+rurl);
			lists.get(i).getFileDto().setFile_rurl(rurl.substring(rurl.indexOf("\\PaRaPaRa"), rurl.length()));
			System.out.println("업주 파일의 상대경로 자르기 후 : "+lists.get(i).getFileDto().getFile_rurl());
		}
		System.out.println("전체 메뉴 조회 : " + lists);
		model.addAttribute("menuList", lists);
		return "/menu/menuListAdmin";
	}

	// 담당자 : 메뉴 삭제
	@RequestMapping(value = "/reSellMenu.do", method = RequestMethod.GET)
	public String reSellMenu(String menu_seq, Model model) {
		System.out.println("넘겨받은 menu_seq : " + menu_seq);
		boolean isc = menu_IService.reSellMenu(menu_seq);
		System.out.println("담당자 메뉴 재판매 등록 성공? : " + isc);
		String menu_category = "주메뉴";
		MenuDto dto = new MenuDto();
		dto.setMenu_category(menu_category);
		List<MenuDto> lists = menu_IService.allMenu(dto);
		for (int i = 0; i < lists.size(); i++) {
			String rurl = lists.get(i).getFileDto().getFile_rurl();
			System.out.println("업주 파일의 상대경로 자르기 전 : "+rurl);
			lists.get(i).getFileDto().setFile_rurl(rurl.substring(rurl.indexOf("\\PaRaPaRa"), rurl.length()));
			System.out.println("업주 파일의 상대경로 자르기 후 : "+lists.get(i).getFileDto().getFile_rurl());
		}
		System.out.println("전체 메뉴 조회 : " + lists);
		model.addAttribute("menuList", lists);
		return "/menu/menuListAdmin";
	}
}
