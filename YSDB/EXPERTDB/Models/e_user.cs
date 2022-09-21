using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;
namespace EXPERTDB.Models
{
    public class e_user
    {

        [Key]
        public int id { get; set; }

        [MaxLength(50), Required]
        public string username { get; set; }
        [MaxLength(50), Required]
        public string password { get; set; }
        [MaxLength(30), Required]
        public string realName { get; set; }
        [MaxLength(100)]
        public string logoPath { get; set; }
        [MaxLength(50)]
        public string email { get; set; }
        ///<summary>
        /// 手机号
        ///</summary>
        [MaxLength(20), Required]
        public string mobilePhone { get; set; }
        ///<summary>
        ///证件类型 0 身份证，1护照
        ///</summary>
        public int idType { get; set; }
        [MaxLength(30), Required]
        public string idNo { get; set; }
         ///<summary>
        ///1 女 2男
        ///</summary>
        public int gender { get; set; }
        public DateTime? birthday { get; set; }
        ///<summary>
        ///专家类型
        ///</summary>
        public int eType { get; set; }
        ///<summary>
        ///职称
        ///</summary>
        [MaxLength(50),Required]
        public string title { get; set; }
        
        ///<summary>
        ///禁用 0不禁用，1 禁用
        ///</summary>
        public bool disabled { get; set; }
        public DateTime createTime { get; set; }
        public DateTime lastUpdateTime { get; set; }
    }
}