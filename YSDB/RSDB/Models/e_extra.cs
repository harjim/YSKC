using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;
namespace RSDB.Models
{
    public class e_extra : tablebase
    {
        [Key]
        public int id { get; set; }

        public int eUserId { get; set; }

        [MaxLength(100)]
        public string homePage { get; set; }

        [MaxLength(200)]
        public string workExp { get; set; }
        [MaxLength(200)]
        public string workResult { get; set; }
        ///<summary>
        ///是否有海外留学，工作经验
        ///</summary>
        public bool? overseaExp { get; set; }
        ///<summary>
        ///海外留学，工作经验
        ///</summary>
        [MaxLength(200)]
        public string overseaContent { get; set; }
        ///<summary>
        ///最高奖励或荣誉称号
        ///</summary>
        [MaxLength(50)]
        public string highestReward { get; set; }
        ///<summary>
        ///学术情况简介
        ///</summary>
        [MaxLength(200)]
        public string academicContent { get; set; }
        ///<summary>
        ///关键字
        ///</summary>
        [MaxLength(200)]
        public string keyWord { get; set; }
        ///<summary>
        ///特殊津贴
        ///</summary>
        [MaxLength(200)]
        public string specialBenefit { get; set; }
        ///<summary>
        ///社会兼职
        ///</summary>
        [MaxLength(200)]
        public string socialAppointments { get; set; }
        ///<summary>
        ///其他职业资格证书
        /// </summary>
        [MaxLength(100)]
        public string otherGNVQsPath { get; set; }
        ///<summary>
        /// 其他文件
        /// </summary>
        [MaxLength(200)]
        public string otherPath { get; set; }


        [MaxLength(200)]
        public string remark { get; set; }

    }
}