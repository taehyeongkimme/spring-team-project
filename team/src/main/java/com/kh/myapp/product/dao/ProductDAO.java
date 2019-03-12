package com.kh.myapp.product.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.kh.myapp.product.dto.ProductDTO;
import com.kh.myapp.product.dto.filesDTO;

public interface ProductDAO {
	
	public void getProductList(Model model, HttpServletRequest request) throws Exception;
	
	public List<ProductDTO> list(int startRec, int endRec);
	
	public int totalrec();
	
	public int insertProduct(ProductDTO productdto);
	
	public boolean deleteProduct(int product_srl);
	
	public boolean modifyProduct(ProductDTO productdto);

	public List<ProductDTO> productList(ProductDTO productdto);

	public ProductDTO dispProduct(ProductDTO productdto);

	public int selectProductByOptionCount(ProductDTO productdto);

	public boolean insertInsertedFile(filesDTO filesdto);

	List<ProductDTO> selectProductByOption(ProductDTO productdto);
	
}