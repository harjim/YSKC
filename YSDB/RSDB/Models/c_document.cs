using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace RSDB.Models
{   
    ///<summary>
    ///公司年度文档表
    ///</summary>
    public class c_document : tablebase
    {
        [Key]
        public int id { get; set; }
        public int companyId { get; set; }
        public int year { get; set; }
        ///<summary>
        /// 类型:0:核算风险报告,1:核算标准方法
        public int type { get; set; }
        [Required, Column(TypeName = "text")]
        public string data { get; set; }
    }
}
