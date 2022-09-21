using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class createProposal_listTable : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "projectId",
                table: "proposal_management",
                nullable: true);

            migrationBuilder.AlterColumn<string>(
                name: "involvedProduct",
                table: "p_project",
                maxLength: 200,
                nullable: true,
                oldClrType: typeof(string),
                oldMaxLength: 50,
                oldNullable: true);

            migrationBuilder.CreateTable(
                name: "proposal_list",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    msCreatorId = table.Column<int>(nullable: false),
                    msLastUpdatorId = table.Column<int>(nullable: false),
                    companyId = table.Column<int>(nullable: false),
                    projectId = table.Column<int>(nullable: true),
                    proposalDate = table.Column<DateTime>(nullable: false),
                    pname = table.Column<string>(maxLength: 200, nullable: false),
                    formula = table.Column<int>(nullable: false),
                    deptName = table.Column<string>(maxLength: 200, nullable: false),
                    beginDate = table.Column<DateTime>(nullable: false),
                    endDate = table.Column<DateTime>(nullable: false),
                    tBeginDate = table.Column<DateTime>(nullable: true),
                    tEndDate = table.Column<DateTime>(nullable: true),
                    initiator = table.Column<string>(maxLength: 50, nullable: false),
                    involvedProduct = table.Column<string>(maxLength: 200, nullable: true),
                    members = table.Column<string>(maxLength: 500, nullable: true),
                    equipments = table.Column<string>(maxLength: 500, nullable: true),
                    situation = table.Column<string>(maxLength: 2000, nullable: false),
                    research = table.Column<string>(type: "text", nullable: false),
                    comparison = table.Column<string>(maxLength: 800, nullable: true),
                    progressPlan = table.Column<string>(maxLength: 800, nullable: true),
                    target = table.Column<string>(maxLength: 800, nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_proposal_list", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "proposal_list");

            migrationBuilder.DropColumn(
                name: "projectId",
                table: "proposal_management");

            migrationBuilder.AlterColumn<string>(
                name: "involvedProduct",
                table: "p_project",
                maxLength: 50,
                nullable: true,
                oldClrType: typeof(string),
                oldMaxLength: 200,
                oldNullable: true);
        }
    }
}
