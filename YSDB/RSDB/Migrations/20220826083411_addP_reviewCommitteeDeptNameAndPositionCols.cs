using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class addP_reviewCommitteeDeptNameAndPositionCols : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "deptName",
                table: "p_reviewCommittee",
                maxLength: 100,
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "position",
                table: "p_reviewCommittee",
                maxLength: 20,
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "deptName",
                table: "p_reviewCommittee");

            migrationBuilder.DropColumn(
                name: "position",
                table: "p_reviewCommittee");
        }
    }
}
