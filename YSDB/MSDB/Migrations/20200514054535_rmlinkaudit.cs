using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class rmlinkaudit : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "project_audit");

            migrationBuilder.DropTable(
                name: "project_link");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "project_audit",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    auditStatus = table.Column<int>(nullable: false),
                    auditTime = table.Column<DateTime>(nullable: false),
                    auditor = table.Column<int>(nullable: false),
                    bAuditOpinion = table.Column<string>(maxLength: 200, nullable: true),
                    bAuditTime = table.Column<DateTime>(nullable: true),
                    bAuditorId = table.Column<int>(nullable: false),
                    content = table.Column<string>(maxLength: 200, nullable: false),
                    fAuditOpinion = table.Column<string>(maxLength: 200, nullable: true),
                    fAuditTime = table.Column<DateTime>(nullable: true),
                    fAuditorId = table.Column<int>(nullable: false),
                    fDeptId = table.Column<int>(nullable: false),
                    fGroupId = table.Column<int>(nullable: false),
                    projectId = table.Column<int>(nullable: false),
                    tAuditOpinion = table.Column<string>(maxLength: 200, nullable: true),
                    tAuditTime = table.Column<DateTime>(nullable: true),
                    tAuditorId = table.Column<int>(nullable: false),
                    tDeptId = table.Column<int>(nullable: false),
                    tGroupId = table.Column<int>(nullable: false),
                    type = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_project_audit", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "project_link",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    createTime = table.Column<DateTime>(nullable: false),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    linkTel = table.Column<string>(maxLength: 20, nullable: false),
                    linkman = table.Column<string>(maxLength: 20, nullable: false),
                    mType = table.Column<int>(nullable: false),
                    projectId = table.Column<int>(nullable: false),
                    remark = table.Column<string>(maxLength: 200, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_project_link", x => x.id);
                });
        }
    }
}
