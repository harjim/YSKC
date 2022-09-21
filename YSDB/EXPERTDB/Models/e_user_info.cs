using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;
namespace EXPERTDB.Models
{
    public class e_user_info : tablebase
    {
        [Key]
        public int id { get; set; }
        public int eUserId { get; set; }
        ///<summary>
        /// 联系电话（办公室）
        ///</summary>
        [MaxLength(20)]
        public string officeTel { get; set; }
        ///<summary>
        /// 联系电话（住宅）
        ///</summary>
        [MaxLength(20)]
        public string homeTel { get; set; }
        [MaxLength(50)]
        public string fax { get; set; }
        ///<summary>
        /// 邮政编码
        ///</summary>
        [MaxLength(10)]
        public string postCode { get; set; }
        ///<summary>
        /// 其他资格证书
        ///</summary>
        [MaxLength(100)]
        public string otherNVQ { get; set; }
        ///<summary>
        /// 工作类型
        ///</summary>
        [MaxLength(50)]
        public string workType { get; set; }
        ///<summary>
        ///职务，职位
        ///</summary>
        [MaxLength(50)]
        public string position { get; set; }
        ///<summary>
        ///工作单位
        ///</summary>
        [MaxLength(50), Required]
        public string unitName { get; set; }
        ///<summary>
        ///通信地址
        ///</summary>
        [MaxLength(200), Required]
        public string address { get; set; }
        [MaxLength(50)]
        public string deptName { get; set; }
        ///<summary>
        ///单位类别
        ///</summary>
        public int? unitType { get; set; }
        ///<summary>
        ///工作城市
        ///</summary>
        [MaxLength(50), Required]
        public String workCity { get; set; }

        ///<summary>
        ///民族
        ///</summary>
        [MaxLength(10)]
        public string nation { get; set; }
        ///<summary>
        /// 学历
        ///</summary>
        public int? eduLevel { get; set; }
        ///<summary>
        /// 学位
        ///</summary>
        public int? degree { get; set; }
        ///<summary>
        /// 毕业院校
        ///</summary>
        [MaxLength(50)]
        public string graduatedSchool { get; set; }
        [MaxLength(50)]
        public string degreeSchool { get; set; }
        public DateTime? graduatedDate { get; set; }
        [MaxLength(50), Required]
        public string major { get; set; }
        ///<summary>
        /// 博士级别： 博士后，博士生导师，院士(多选)
        ///</summary>
        [MaxLength(50)]
        public string doctorLevel { get; set; }
        ///<summary>
        /// 最熟悉领域
        ///</summary>
        [MaxLength(50)]
        public string firstSubject { get; set; }
        ///<summary>
        /// 次熟悉领域
        ///</summary>
        [MaxLength(50)]
        public string secondSubject { get; set; }
        ///<summary>
        /// 较熟悉领域
        ///</summary>
        [MaxLength(50)]
        public string thirdSubject { get; set; }
        ///<summary>
        /// 行业类型（传统，新兴）（多选）
        ///</summary>
        [MaxLength(10), Required]
        public string industryType { get; set; }

        ///<summary>
        /// 传统行业(行业类型选传统)
        ///</summary>
        [MaxLength(50)]
        public string tradition { get; set; }
        ///<summary>
        /// 新兴行业（行业类型选新兴）
        ///</summary>
        [MaxLength(50)]
        public string booming { get; set; }
        public bool isGovReview { get; set; }
        [MaxLength(200)]
        public string govReviewName { get; set; }
        [MaxLength(2000)]
        public string other { get; set; }
    }
}