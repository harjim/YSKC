using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class createSoftAndICRegistrationTables : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "claimContent",
                table: "patent",
                maxLength: 1000,
                nullable: false,
                defaultValue: "");

            migrationBuilder.AddColumn<int>(
                name: "claimNum",
                table: "patent",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<string>(
                name: "specification",
                table: "patent",
                maxLength: 2000,
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "specificationFile",
                table: "patent",
                maxLength: 500,
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "usedCnt",
                table: "patent",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.CreateTable(
                name: "ICRegistration",
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
                    desginName = table.Column<string>(maxLength: 200, nullable: false),
                    registerNo = table.Column<string>(maxLength: 50, nullable: false),
                    applyDate = table.Column<DateTime>(nullable: false),
                    ownerName = table.Column<string>(maxLength: 200, nullable: true),
                    ownerAddress = table.Column<string>(maxLength: 200, nullable: true),
                    issueDate = table.Column<DateTime>(nullable: false),
                    certificateNo = table.Column<string>(maxLength: 50, nullable: true),
                    filepath = table.Column<string>(maxLength: 1000, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_ICRegistration", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "softRegistration",
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
                    softName = table.Column<int>(maxLength: 100, nullable: false),
                    ownerName = table.Column<int>(maxLength: 200, nullable: false),
                    issueDate = table.Column<DateTime>(nullable: false),
                    registerNo = table.Column<string>(maxLength: 50, nullable: false),
                    certificateNo = table.Column<string>(maxLength: 50, nullable: true),
                    filepath = table.Column<string>(maxLength: 1000, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_softRegistration", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "ICRegistration");

            migrationBuilder.DropTable(
                name: "softRegistration");

            migrationBuilder.DropColumn(
                name: "claimContent",
                table: "patent");

            migrationBuilder.DropColumn(
                name: "claimNum",
                table: "patent");

            migrationBuilder.DropColumn(
                name: "specification",
                table: "patent");

            migrationBuilder.DropColumn(
                name: "specificationFile",
                table: "patent");

            migrationBuilder.DropColumn(
                name: "usedCnt",
                table: "patent");
        }
    }
}
