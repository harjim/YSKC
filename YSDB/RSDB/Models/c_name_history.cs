using System;
using System.ComponentModel.DataAnnotations;
namespace RSDB.Models
{
    public class c_name_history : tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 公司ID 
        /// </summary>
        public int companyId { get; set; }
        /// <summary>
        /// 公司名
        /// </summary>
        [Required,MaxLength(50)]
        public string companyName { get; set; }

        /// <summary>
        ///启用日期
        /// </summary>
        public DateTime startDate { get; set; }

        /// <summary>
        /// 备注
        /// </summary>
        [MaxLength(200)]
        public String remark { get; set; }
        
    }
}