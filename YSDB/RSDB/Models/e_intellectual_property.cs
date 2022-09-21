using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;
namespace RSDB.Models
{
    public class e_intellectual_property : tablebase
    {
        [Key]
        public int id { get; set; }
        public int eUserId { get; set; }
        [MaxLength(100),Required]
        public string pname { get; set; }
        public int type { get; set; }
        public int country { get; set; }
        ///<summary>
        /// 申请号
        ///</summary>
        [MaxLength(30)]
        public string appNo { get; set; }
        ///<summary>
        /// 授权号
        ///</summary>
        [MaxLength(30)]
        public string grantNo { get; set; }
        ///<summary>
        /// 专利类型  '1': '发明专利','2': '实用新型', '3': '外观设计'
        ///</summary>
        public int? patentType { get; set; }
        ///<summary>
        /// 转化
        ///</summary>
        public bool transform { get; set; }
        ///<summary>
        /// 发证单位
        /// </summary>
        [MaxLength(50)]
        public string issueUnit { get; set; }
        ///<summary>
        /// 证书号
        ///</summary>
        [MaxLength(30)]
        public string certificateNo { get; set; }

    }
}