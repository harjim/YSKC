using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class addFlowBranchTypeAndFlowNodeParentIdCols : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "parentId",
                table: "flowNode",
                nullable: true);

            migrationBuilder.AlterColumn<string>(
                name: "condition",
                table: "flowBranch",
                maxLength: 200,
                nullable: false,
                oldClrType: typeof(string),
                oldMaxLength: 50);

            migrationBuilder.AddColumn<int>(
                name: "type",
                table: "flowBranch",
                nullable: false,
                defaultValue: 0);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "parentId",
                table: "flowNode");

            migrationBuilder.DropColumn(
                name: "type",
                table: "flowBranch");

            migrationBuilder.AlterColumn<string>(
                name: "condition",
                table: "flowBranch",
                maxLength: 50,
                nullable: false,
                oldClrType: typeof(string),
                oldMaxLength: 200);
        }
    }
}
