package com.data.mvc.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.data.mvc.model.Import;
import com.data.mvc.model.ImportParam;
@Mapper
public interface ImportMapper {
	void insert(Import im);
	void update(Import im);
	void delete(Integer id);
	Import selectOne(Integer id);
	List<Import> selectList(ImportParam param);
	
	void insertIn(@Param("imid") Integer imid,@Param("inid") Integer inid);
	void insertOut(@Param("imid")Integer imid,@Param("outid")Integer outid);
	void insertDb(@Param("imid")Integer imid,@Param("dbid")Integer dbid);
	void insertTh(@Param("imid") Integer imid,@Param("thid") Integer thinid);
	void deleteIn(@Param("imid")Integer imid);
	void deleteOut(@Param("imid")Integer imid);
	void deleteDb(@Param("imid")Integer imid);
	void deleteTh(@Param("imid")Integer imid);
}
