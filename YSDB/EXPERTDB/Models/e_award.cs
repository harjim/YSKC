using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace EXPERTDB.Models
{
    public class e_award : tablebase
    {
        [Key]
        public int id { get; set; }
        public int eUserId { get; set; }
        ///<summary>
        ///获奖项目
        ///</summary> 
        [MaxLength(50), Required]
        public string aProject { get; set; }
        ///<summary>
        ///获奖日期
        ///</summary>
        public DateTime grantDate { get; set; } 
        ///<summary>
        ///获奖名称
        ///</summary> 
        [MaxLength(50), Required]
        public string awordName { get; set; }
         ///<summary>
        ///获奖类型：0国家级，1省部级，2地级 3其他
        ///</summary> 
        public int type{get;set;}
        [MaxLength(50), Required]
        public string ranking{get;set;}
        ///<smmary>
        ///授奖部门
        ///</summary>
        [MaxLength(50), Required]
        public string grantDept{get;set;}
    }
}