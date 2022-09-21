using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace ESDB.Models
{
    public class user_info : tablebase
    {
        [Key]
        public int id { get; set; }
        public int userId { get; set; }

        ///<summary>
        ///籍贯
        ///</summary>
        [MaxLength(50), Required]
        public string nativePlace { get; set; }

        ///<summary>
        /// 学历 0: 研究生，1：本科，2：大专，3：其他
        ///</summary>
        public int eduLevel { get; set; }
        ///<summary>
        /// 学位 0：博士，1：硕士，2：学士，3：无
        ///</summary>
        public int degree { get; set; }

        ///<summary>
        /// 职称
        ///</summary>
        [MaxLength(50)]
        public string titles { get; set; }
        ///<summary>
        /// 其他职称
        ///</summary>
        [MaxLength(50)]
        public string otherTitles { get; set; }

        ///<summary>
        /// 毕业院校
        ///</summary>
        [MaxLength(50)]
        public string graduatedSchool { get; set; }
        ///<summary>
        ///研究领域
        ///</summary>
        [MaxLength(50)]
        public string researchArea { get; set; }
        ///<summary>
        ///熟悉的行业【多选】
        /// </summary>
        [MaxLength(50)]
        public string industries { get; set; }

        ///<summary>
        ///工作单位
        ///</summary>
        [MaxLength(50)]
        public string unitName { get; set; }

        ///<summary>
        ///单位性质 
        ///</summary>
        [MaxLength(20)]
        public string unitType { get; set; }
        ///<summary>
        ///职务
        ///</summary>
        [MaxLength(50)]
        public string job { get; set; }
        ///<summary>
        ///部门名称
        ///</summary>
        [MaxLength(50)]
        public string deptName { get; set; }
        /// <summary>
        /// 工作所在地代码
        /// </summary>
        [MaxLength(30)]
        public string unitAddressCode { get; set; }
        /// <summary>
        /// 所在地详细地址
        /// </summary>
        [MaxLength(100)]
        public string unitAddress { get; set; }

        ///<summary>
        /// 工作经历
        ///</summary>
        [Column(TypeName = "text")]
        public string workExperience { get; set; }

    }
}